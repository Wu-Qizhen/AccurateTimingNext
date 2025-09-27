package com.wqz.accuratetimingnext.act.about

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.aethex.matrix.animation.XActivateVfx.clickVfx
import com.wqz.accuratetimingnext.aethex.matrix.ui.XBackground
import com.wqz.accuratetimingnext.aethex.matrix.ui.XBar

/**
 * 关于组织
 * Created by Wu Qizhen on 2024.6.22
 */
class AboutStudioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            XBackground.Breathing{
                AboutStudioScreen()
            }
        }
    }

    @Composable
    fun AboutStudioScreen() {
        // val context = LocalContext.current
        val scrollState = rememberScrollState()
        var isVisible by remember {
            mutableStateOf(true)
        }
        val animatedBlur by animateDpAsState(
            targetValue = if (isVisible) 5.dp else 0.dp,
            label = "blur"
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            XBar.Text(title = R.string.about_studio)

            Image(
                painter = painterResource(id = R.drawable.logo_code_intellix_poster),
                contentDescription = null,
                modifier = Modifier
                    .clickVfx { }
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier
                    .clickVfx { isVisible = !isVisible }
                    .blur(radius = animatedBlur, edgeTreatment = BlurredEdgeTreatment.Unbounded),
                text = stringResource(id = R.string.text_studio_describe),
                fontSize = 12.sp
            )

            ClassificationBar(icon = R.drawable.ic_develop, text = R.string.dept_dev)

            Spacer(modifier = Modifier.height(5.dp))

            Image(
                painter = painterResource(id = R.drawable.logo_intellic_lab_poster),
                contentDescription = null,
                modifier = Modifier
                    .clickVfx { }
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.Crop
            )

            ClassificationBar(icon = R.drawable.ic_draw, text = R.string.dept_design)

            Spacer(modifier = Modifier.height(5.dp))

            Image(
                painter = painterResource(id = R.drawable.logo_intellid_studio_poster),
                contentDescription = null,
                modifier = Modifier
                    .clickVfx { }
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.Crop
            )

            ClassificationBar(icon = R.drawable.ic_light, text = R.string.dept_create)

            Spacer(modifier = Modifier.height(5.dp))

            Image(
                painter = painterResource(id = R.drawable.logo_intellia_visual_poster),
                contentDescription = null,
                modifier = Modifier
                    .clickVfx { }
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(50.dp))
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
}