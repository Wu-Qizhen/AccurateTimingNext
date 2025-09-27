package com.wqz.accuratetimingnext.act.about

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.aethex.matrix.animation.XActivateVfx.clickVfx
import com.wqz.accuratetimingnext.aethex.matrix.ui.XBackground
import com.wqz.accuratetimingnext.aethex.matrix.ui.XItem

/**
 * 关于应用
 * Created by Wu Qizhen on 2024.6.22
 */
class AboutAppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            XBackground.Circles {
                AboutAppScreen()
            }
        }
    }

    @Composable
    fun AboutAppScreen() {
        val context = LocalContext.current
        val scrollState = rememberScrollState()
        val showBuild = remember { mutableStateOf(false) }
        val developerNames = listOf(
            R.string.wqz,
            R.string.chatglm,
            R.string.tylm,
            R.string.deepseek,
            R.string.codegeex,
            R.string.sd
        )
        val developerDesc = listOf(
            R.string.wqz_desc,
            R.string.chatglm_desc,
            R.string.tylm_desc,
            R.string.deepseek_desc,
            R.string.codegeex_desc,
            R.string.sd_desc
        )
        val developerImages = listOf(
            R.drawable.logo_wqz,
            R.drawable.logo_chatglm,
            R.drawable.logo_tylm,
            R.drawable.logo_deepseek,
            R.drawable.logo_codegeex,
            R.drawable.logo_sd
        )
        val developerDetails = listOf(
            R.string.text_about_wqz,
            R.string.text_about_chatglm,
            R.string.text_about_tylm,
            R.string.text_about_deepseek,
            R.string.text_about_codegeex,
            R.string.text_about_sd
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            TitleBar(title = R.string.about_us)

            Spacer(modifier = Modifier.height(10.dp))

            Image(
                painter = painterResource(id = R.drawable.logo_at_for_wear_os),
                contentDescription = stringResource(id = R.string.app_name),
                modifier = Modifier
                    .height(50.dp)
                    .clickVfx()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.introduction),
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = FontFamily(
                    Font(R.font.fzruisuti_regular, FontWeight.Normal)
                ),
                fontWeight = FontWeight.Thin,
                modifier = Modifier.clickVfx()
            )

            Spacer(modifier = Modifier.height(5.dp))

            IconButtonWithTopSpacer(
                icon = R.drawable.ic_version,
                text = R.string.version,
                subText = R.string.version_desc
            ) {
                context.startActivity(Intent(context, UpdateLogActivity::class.java))
            }

            IconButtonWithTopSpacer(
                icon = R.drawable.logo_aethex_matrix,
                text = R.string.aethex_matrix,
                subText = R.string.framework
            ) { }

            ClassificationBar(icon = R.drawable.ic_team, text = R.string.participating_team)

            Spacer(modifier = Modifier.height(5.dp))

            XItem.Capsule(
                image = R.drawable.logo_code_intellix,
                text = stringResource(id = R.string.studio),
                subText = stringResource(id = R.string.studio_desc)
            ) {
                context.startActivity(Intent(context, AboutStudioActivity::class.java))
            }

            ClassificationBar(
                icon = R.drawable.ic_member,
                text = R.string.participating_individuals
            )

            for ((index) in developerNames.withIndex()) {
                Spacer(modifier = Modifier.height(5.dp))

                XItem.Capsule(
                    image = developerImages[index],
                    text = stringResource(developerNames[index]),
                    subText = stringResource(developerDesc[index])
                ) {
                    val intent = Intent(context, AboutDeveloperActivity::class.java)
                    intent.putExtra("logo", developerImages[index])
                    intent.putExtra("name", developerNames[index])
                    intent.putExtra("description", developerDesc[index])
                    intent.putExtra("details", developerDetails[index])
                    context.startActivity(intent)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            AnimatedVisibility(
                visible = showBuild.value,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .clickVfx {
                            showBuild.value = false
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.logo_aethex_matrix),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                            .padding(end = 5.dp),
                        tint = Color.White
                    )

                    Text(
                        text = stringResource(id = R.string.powered_by),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            AnimatedVisibility(
                visible = !showBuild.value,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .clickVfx {
                            showBuild.value = true
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = stringResource(id = R.string.copyright),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    @Composable
    fun IconButtonWithTopSpacer(icon: Int, text: Int, subText: Int, onClick: () -> Unit) {
        Spacer(modifier = Modifier.height(5.dp))

        XItem.Capsule(
            icon = icon,
            iconSize = 20,
            text = stringResource(text),
            subText = stringResource(subText)
        ) {
            onClick()
        }
    }

    @Composable
    fun ClassificationBar(icon: Int, text: Int) {
        Row(
            modifier = Modifier
                .clickVfx()
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = text),
                modifier = Modifier.size(25.dp)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = stringResource(id = text),
                fontWeight = FontWeight.Bold,
            )
        }
    }

    @Composable
    fun TitleBar(title: Int) {
        val context = LocalContext.current
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()

        Row(
            modifier = Modifier
                .height(40.dp)
                .clickVfx(
                    interactionSource = interactionSource
                ) {
                    (context as? ComponentActivity)?.finish()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = isPressed.value,
                enter = expandHorizontally() + fadeIn(),
                exit = shrinkHorizontally() + fadeOut()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    modifier = Modifier.size(18.dp),
                    tint = Color.White
                )
            }

            Text(
                text = stringResource(id = title),
                fontSize = 22.sp,
                // fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.fzruisuti_regular)),
                maxLines = 1,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}