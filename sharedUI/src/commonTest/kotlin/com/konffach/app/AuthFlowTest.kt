package com.konffach.app

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import com.konffach.app.di.AppGraph
import com.konffach.app.features.auth.api.AuthRepository
import com.konffach.app.features.auth.api.SessionState
import com.konffach.app.features.auth.api.TokensRepository
import com.konffach.app.features.auth.screen.AuthTokens
import com.konffach.app.features.auth.ui.AuthScreenTestTags
import com.konffach.app.features.auth.ui.AuthViewModel
import com.konffach.app.features.chat.api.ChatRepository
import com.konffach.app.features.chat.screen.ChatMessage
import com.konffach.app.features.chat.ui.ChatViewModel
import com.konffach.app.features.dialogs.screen.ChatSummary
import com.konffach.app.features.dialogs.ui.DialogsViewModel
import com.konffach.app.features.home.api.HomeRepository
import com.konffach.app.features.home.screen.HomeItem
import com.konffach.app.features.home.ui.HomeScreenTestTags
import com.konffach.app.features.home.ui.HomeViewModel
import com.konffach.app.features.settings.ui.SettingsScreenTestTags
import com.konffach.app.features.settings.ui.SettingsViewModel
import com.konffach.app.navigation.NavigationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class AuthFlowTest {

    @Test
    fun signInOpenSettingsClearTokenReturnsToAuth() = runComposeUiTest {
        val fakeAppGraph = FakeAppGraph()

        setContent {
            AppContent(appGraph = fakeAppGraph)
        }

        onNodeWithTag(AuthScreenTestTags.LOGIN_INPUT)
            .performTextInput("123")
        onNodeWithTag(AuthScreenTestTags.PASSWORD_INPUT)
            .performTextInput("123")
        onNodeWithTag(AuthScreenTestTags.SIGN_IN_BUTTON)
            .performClick()

        waitUntil(timeoutMillis = 5_000) {
            runCatching { onNodeWithText("Home").fetchSemanticsNode() }.isSuccess
        }
        onNodeWithText("Home").fetchSemanticsNode()

        onNodeWithTag(HomeScreenTestTags.SETTINGS_BUTTON).performClick()

        waitUntil(timeoutMillis = 5_000) {
            runCatching { onNodeWithText("Settings").fetchSemanticsNode() }.isSuccess
        }
        onNodeWithTag(SettingsScreenTestTags.LOG_OUT).performClick()

        waitUntil(timeoutMillis = 5_000) {
            onAllNodesWithTag(AuthScreenTestTags.SIGN_IN_BUTTON).fetchSemanticsNodes().isNotEmpty()
        }
        onNodeWithTag(AuthScreenTestTags.SIGN_IN_BUTTON).fetchSemanticsNode()
    }
}

private class FakeAppGraph : AppGraph {
    private val tokenRepository = FakeTokensRepository()
    private val authRepository = FakeAuthRepository()
    private val homeRepository = FakeHomeRepository()
    private val chatRepository = FakeChatRepository()

    override val navigationViewModel: NavigationViewModel =
        NavigationViewModel(tokenRepository)

    override val authViewModelFactory: AuthViewModel.Factory =
        AuthViewModel.Factory {
            AuthViewModel(
                authRepository = authRepository,
                tokenRepository = tokenRepository,
            )
        }

    override val homeViewModelFactory: HomeViewModel.Factory =
        HomeViewModel.Factory {
            HomeViewModel(homeRepository = homeRepository)
        }

    override val settingsViewModelFactory: SettingsViewModel.Factory =
        SettingsViewModel.Factory {
            SettingsViewModel(tokenRepository = tokenRepository)
        }

    override val dialogsViewModelFactory: DialogsViewModel.Factory =
        DialogsViewModel.Factory {
            DialogsViewModel(repository = chatRepository)
        }

    override val chatViewModelFactory: ChatViewModel.Factory =
        ChatViewModel.Factory { dialogId ->
            ChatViewModel(
                dialogId = dialogId,
                repository = chatRepository,
            )
        }
}

private class FakeAuthRepository : AuthRepository {
    override suspend fun signIn(login: String, password: String): AuthTokens {
        return if (login == "123" && password == "123") {
            AuthTokens(
                accessToken = "access-token",
                refreshToken = "refresh-token",
            )
        } else {
            throw IllegalStateException("Error with username pr password")
        }
    }

    override suspend fun signUp(login: String, password: String) = signIn(login, password)
}

private class FakeTokensRepository : TokensRepository {
    private val mutableSessionState = MutableStateFlow<SessionState>(SessionState.Unauthenticated)

    override val sessionState: StateFlow<SessionState> = mutableSessionState.asStateFlow()

    override suspend fun save(tokens: AuthTokens) {
        mutableSessionState.value = SessionState.Authenticated(tokens)
    }

    override fun clear() {
        mutableSessionState.value = SessionState.Unauthenticated
    }
}

private class FakeHomeRepository : HomeRepository {
    override suspend fun loadItems(): List<HomeItem> = listOf(
        HomeItem(id = "1", title = "Welcome", subtitle = "Your conversations"),
    )
}

private class FakeChatRepository : ChatRepository {
    override fun getChats(): List<ChatSummary> = listOf(
        ChatSummary(
            id = "1",
            title = "Test chat",
            lastMessage = "Hello",
            avatarUrl = "",
        )
    )

    override fun getMessages(dialogId: String): List<ChatMessage> = listOf(
        ChatMessage(
            id = "1",
            dialogId = dialogId,
            author = "Tester",
            text = "Message",
            isMine = true,
        )
    )
}
