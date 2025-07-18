@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.wqz.accuratetimingnext.act.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.wqz.accuratetimingnext.act.game.util.Mode
import com.wqz.accuratetimingnext.act.rank.RankSelectActivity
import com.wqz.accuratetimingnext.act.record.RecordListActivity
import com.wqz.accuratetimingnext.act.setting.SettingActivity
import com.wqz.accuratetimingnext.act.time.TimeSelectActivity
import com.wqz.accuratetimingnext.preference.LayoutPreferencesManager
import com.wqz.accuratetimingnext.ui.ModifierExtends.clickVfx
import com.wqz.accuratetimingnext.ui.XBackground
import com.wqz.accuratetimingnext.ui.XBar
import com.wqz.accuratetimingnext.ui.XItem
import com.wqz.accuratetimingnext.ui.XToast
import kotlinx.coroutines.delay

/**
 * 主界面
 * Created by Wu Qizhen on 2024.6.29
 */
class MainActivity : ComponentActivity() {
    private lateinit var preferencesManager: LayoutPreferencesManager

    // 添加呼吸状态控制
    private var isBreathing = mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferencesManager = LayoutPreferencesManager(this)

        setContent {
            XBackground.BreathingBackground(isBreathing = isBreathing.value) {
                LaunchAnimation(
                    isGirdLayout = preferencesManager.isGridLayout(),
                    // 添加回调用于停止呼吸
                    onTransitionComplete = { isBreathing.value = false }
                )
            }
        }
    }

    @Composable
    fun LaunchScreen(
        sharedTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope
    ) {
        var isVisible by remember {
            mutableStateOf(true)
        }
        val animatedBlur by animateDpAsState(
            targetValue = if (isVisible) 5.dp else 0.dp,
            label = "blur"
        )

        LaunchedEffect(key1 = Unit) {
            delay(100)
            isVisible = false
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                with(sharedTransitionScope) {
                    Box(modifier = Modifier.wrapContentSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_background_frosted),
                            contentDescription = "Logo Background",
                            modifier = Modifier
                                .size(80.dp)
                                .blur(
                                    radius = animatedBlur,
                                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                                )
                        )

                        Image(
                            painter = painterResource(id = R.drawable.logo_at_watch),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .sharedElement(
                                    rememberSharedContentState(key = "logo"),
                                    animatedVisibilityScope = animatedVisibilityScope
                                )
                                .size(40.dp)
                                .offset(x = 25.dp, y = (15).dp)
                                .blur(
                                    radius = animatedBlur,
                                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                                )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Image(
                        painter = painterResource(id = R.drawable.logo_at_text),
                        contentDescription = "Text",
                        modifier = Modifier
                            .sharedElement(
                                rememberSharedContentState(key = "text"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                            .size(width = 100.dp, height = 30.dp)
                            .blur(
                                radius = animatedBlur,
                                edgeTreatment = BlurredEdgeTreatment.Unbounded
                            )
                    )
                }
            }

            Text(
                text = stringResource(id = R.string.slogan),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.fzruisuti_regular)),
                fontWeight = FontWeight.Thin,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
                    .blur(
                        radius = animatedBlur,
                        edgeTreatment = BlurredEdgeTreatment.Unbounded
                    )
            )
        }
    }

    @Composable
    fun MainScreen(
        sharedTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope,
        isGirdLayout: Boolean
    ) {
        val context = LocalContext.current
        val scrollState = rememberScrollState()
        val showBuild = remember { mutableStateOf(true) }
        /*val backgroundColor = Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,  // 顶部颜色：全透明
                Color.Black.copy(alpha = 0.65f)  // 底部颜色：黑色
            )
        )*/
        var isVisible by remember {
            mutableStateOf(false)
        }
        val animatedBlur by animateDpAsState(
            targetValue = if (isVisible) 5.dp else 0.dp,
            label = "blur"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    // .background(backgroundColor)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 25.dp, bottom = 10.dp)
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    with(sharedTransitionScope) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_at_watch),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .sharedElement(
                                    rememberSharedContentState(key = "logo"),
                                    animatedVisibilityScope = animatedVisibilityScope
                                )
                                .size(25.dp)
                                .blur(
                                    radius = animatedBlur,
                                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                                ),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Image(
                            painter = painterResource(id = R.drawable.logo_at_text),
                            contentDescription = "WXGY Text",
                            modifier = Modifier
                                .sharedElement(
                                    rememberSharedContentState(key = "text"),
                                    animatedVisibilityScope = animatedVisibilityScope
                                )
                                .width(100.dp)
                                .height(23.dp)
                                .clickVfx { isVisible = !isVisible }
                                .blur(
                                    radius = animatedBlur,
                                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                                ),
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                if (!isGirdLayout) {
                    XBar.ClassificationBar(
                        iconId = R.drawable.ic_grade_easy,
                        textId = R.string.grade_easy
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    XItem.Capsule(
                        icon = R.drawable.ic_flag,
                        text = stringResource(id = R.string.recording_mode)
                    ) {
                        val intent = Intent(context, TimeSelectActivity::class.java)
                        intent.putExtra("MODE", Mode.RACING_MODE.name)
                        context.startActivity(intent)
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    XItem.Capsule(
                        icon = R.drawable.ic_lightning,
                        text = stringResource(id = R.string.practice_mode)
                    ) {
                        val intent = Intent(context, TimeSelectActivity::class.java)
                        intent.putExtra("MODE", Mode.PRACTICE_MODE.name)
                        context.startActivity(intent)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    XBar.ClassificationBar(
                        iconId = R.drawable.ic_grade_hard,
                        textId = R.string.grade_hard
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    XItem.Capsule(
                        icon = R.drawable.ic_blind,
                        text = stringResource(id = R.string.blind_challenge)
                    ) {
                        val intent = Intent(context, TimeSelectActivity::class.java)
                        intent.putExtra("MODE", Mode.BLIND_MODE.name)
                        context.startActivity(intent)
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    XItem.Capsule(
                        icon = R.drawable.ic_memory,
                        text = stringResource(id = R.string.memory_challenge)
                    ) {
                        val intent = Intent(context, TimeSelectActivity::class.java)
                        intent.putExtra("MODE", Mode.MEMORY_MODE.name)
                        context.startActivity(intent)
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    XItem.Capsule(
                        icon = R.drawable.ic_reverse,
                        text = stringResource(id = R.string.reverse_challenge)
                    ) {
                        val intent = Intent(context, TimeSelectActivity::class.java)
                        intent.putExtra("MODE", Mode.REVERSE_MODE.name)
                        context.startActivity(intent)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    XBar.ClassificationBar(
                        iconId = R.drawable.ic_grade_expert,
                        textId = R.string.grade_expert
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    XItem.Capsule(
                        icon = R.drawable.ic_random,
                        text = stringResource(id = R.string.disordered_memory_challenge)
                    ) {
                        val intent = Intent(context, TimeSelectActivity::class.java)
                        intent.putExtra("MODE", Mode.DISORDERED_MEMORY_MODE.name)
                        context.startActivity(intent)
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    XItem.Capsule(
                        icon = R.drawable.ic_calc,
                        text = stringResource(id = R.string.computational_challenge)
                    ) {
                        val intent = Intent(context, TimeSelectActivity::class.java)
                        intent.putExtra("MODE", Mode.COMPUTATIONAL_MODE.name)
                        context.startActivity(intent)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    XBar.ClassificationBar(
                        iconId = R.drawable.ic_grade_endless,
                        textId = R.string.grade_endless
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    XItem.Capsule(
                        icon = R.drawable.ic_rhythm,
                        text = stringResource(id = R.string.rhythm_challenge)
                    ) {
                        XToast.showText("暂未开放")
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    XBar.ClassificationBar(iconId = R.drawable.ic_option, textId = R.string.option)

                    Spacer(modifier = Modifier.height(10.dp))

                    XItem.Capsule(
                        icon = R.drawable.ic_medal,
                        text = stringResource(id = R.string.ranking_list)
                    ) {
                        startActivity(Intent(context, RankSelectActivity::class.java))
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    XItem.Capsule(
                        icon = R.drawable.ic_statistics,
                        text = stringResource(id = R.string.game_record)
                    ) {
                        startActivity(Intent(context, RecordListActivity::class.java))
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    XItem.Capsule(
                        icon = R.drawable.ic_settings,
                        text = stringResource(id = R.string.settings)
                    ) {
                        startActivity(Intent(context, SettingActivity::class.java))
                    }
                } else {
                    XBar.ClassificationBar(
                        iconId = R.drawable.ic_grade_easy,
                        textId = R.string.grade_easy
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        XItem.Card(
                            icon = R.drawable.ic_flag,
                            text = stringResource(id = R.string.recording_mode)
                        ) {
                            val intent = Intent(context, TimeSelectActivity::class.java)
                            intent.putExtra("MODE", Mode.RACING_MODE.name)
                            context.startActivity(intent)
                        }

                        Spacer(modifier = Modifier.width(5.dp))

                        XItem.Card(
                            icon = R.drawable.ic_lightning,
                            text = stringResource(id = R.string.practice_mode)
                        ) {
                            val intent = Intent(context, TimeSelectActivity::class.java)
                            intent.putExtra("MODE", Mode.PRACTICE_MODE.name)
                            context.startActivity(intent)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    XBar.ClassificationBar(
                        iconId = R.drawable.ic_grade_hard,
                        textId = R.string.grade_hard
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        XItem.Card(
                            icon = R.drawable.ic_blind,
                            text = stringResource(id = R.string.blind_challenge)
                        ) {
                            val intent = Intent(context, TimeSelectActivity::class.java)
                            intent.putExtra("MODE", Mode.BLIND_MODE.name)
                            context.startActivity(intent)
                        }

                        Spacer(modifier = Modifier.width(5.dp))

                        XItem.Card(
                            icon = R.drawable.ic_memory,
                            text = stringResource(id = R.string.memory_challenge)
                        ) {
                            val intent = Intent(context, TimeSelectActivity::class.java)
                            intent.putExtra("MODE", Mode.MEMORY_MODE.name)
                            context.startActivity(intent)
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        XItem.Card(
                            icon = R.drawable.ic_reverse,
                            text = stringResource(id = R.string.reverse_challenge)
                        ) {
                            val intent = Intent(context, TimeSelectActivity::class.java)
                            intent.putExtra("MODE", Mode.REVERSE_MODE.name)
                            context.startActivity(intent)
                        }

                        Spacer(modifier = Modifier.width(5.dp))

                        Surface(
                            modifier = Modifier.size(85.dp),
                            color = Color.Transparent
                        ) {}
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    XBar.ClassificationBar(
                        iconId = R.drawable.ic_grade_expert,
                        textId = R.string.grade_expert
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        XItem.Card(
                            icon = R.drawable.ic_random,
                            text = stringResource(id = R.string.disordered_memory_challenge)
                        ) {
                            val intent = Intent(context, TimeSelectActivity::class.java)
                            intent.putExtra("MODE", Mode.DISORDERED_MEMORY_MODE.name)
                            context.startActivity(intent)
                        }

                        Spacer(modifier = Modifier.width(5.dp))

                        XItem.Card(
                            icon = R.drawable.ic_calc,
                            text = stringResource(id = R.string.computational_challenge)
                        ) {
                            val intent = Intent(context, TimeSelectActivity::class.java)
                            intent.putExtra("MODE", Mode.COMPUTATIONAL_MODE.name)
                            context.startActivity(intent)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    XBar.ClassificationBar(
                        iconId = R.drawable.ic_grade_endless,
                        textId = R.string.grade_endless
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        XItem.Card(
                            icon = R.drawable.ic_rhythm,
                            text = stringResource(id = R.string.rhythm_challenge)
                        ) {
                            XToast.showText("暂未开放")
                        }

                        Spacer(modifier = Modifier.width(5.dp))

                        Surface(
                            modifier = Modifier.size(85.dp),
                            color = Color.Transparent
                        ) {}
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    XBar.ClassificationBar(iconId = R.drawable.ic_option, textId = R.string.option)

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        XItem.Card(
                            icon = R.drawable.ic_medal,
                            text = stringResource(id = R.string.ranking_list)
                        ) {
                            startActivity(Intent(context, RankSelectActivity::class.java))
                        }

                        Spacer(modifier = Modifier.width(5.dp))

                        XItem.Card(
                            icon = R.drawable.ic_statistics,
                            text = stringResource(id = R.string.game_record)
                        ) {
                            startActivity(Intent(context, RecordListActivity::class.java))
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        XItem.Card(
                            icon = R.drawable.ic_settings,
                            text = stringResource(id = R.string.settings)
                        ) {
                            startActivity(Intent(context, SettingActivity::class.java))
                        }

                        Spacer(modifier = Modifier.width(5.dp))

                        Surface(
                            modifier = Modifier.size(85.dp),
                            color = Color.Transparent
                        ) {}
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
    }

    /*@Composable
    fun ClassificationBar(icon: Int, text: Int) {
        Row(
            modifier = Modifier
                .clickVfx()
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = text),
                modifier = Modifier.size(20.dp),
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = stringResource(id = text),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.fzruisuti_regular)),
                fontWeight = FontWeight.Thin
            )
        }
    }*/

    // @Preview
    @Composable
    fun LaunchAnimation(
        isGirdLayout: Boolean,
        onTransitionComplete: () -> Unit // 新增回调参数
    ) {
        var isTransitioned by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = Unit) {
            delay(1500)
            isTransitioned = true
            onTransitionComplete() // 通知外部过渡完成
        }

        SharedTransitionLayout {
            AnimatedContent(
                isTransitioned,
                label = "basic_transition"
            ) { targetState ->
                if (!targetState) {
                    LaunchScreen(
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout
                    )
                } else {
                    MainScreen(
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        isGirdLayout = isGirdLayout
                    )
                }
            }
        }
    }
}