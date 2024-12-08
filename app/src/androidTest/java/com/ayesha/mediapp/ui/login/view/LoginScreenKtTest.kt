package com.ayesha.mediapp.ui.login.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoginScreenInteractions() {
        composeTestRule.setContent {
            LoginScreen()
        }

        composeTestRule.onNodeWithTag("UserName").assertExists()
        composeTestRule.onNodeWithTag("Email").assertExists()
        composeTestRule.onNodeWithTag("Login").assertExists()

        composeTestRule.onNodeWithTag("UserName").performTextInput("TestUser")
        composeTestRule.onNodeWithTag("Email").performTextInput("test@example.com")

        composeTestRule.onNodeWithTag("UserName").assertTextEquals("TestUser")
        composeTestRule.onNodeWithTag("Email").assertTextEquals("test@example.com")

        composeTestRule.onNodeWithTag("Login").performClick()

    }
}
