package com.example.dictionary.feature_dictionary.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dictionary.feature_dictionary.domain.model.WordInfo

@Composable
fun WordInfoItem(wordInfo: WordInfo, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = wordInfo.word,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(text = wordInfo.phonetic ?: "", fontWeight = FontWeight.Light)

        Spacer(modifier = Modifier.height(16.dp))

        wordInfo.meanings.forEach { meaning ->
            Text(text = meaning.partOfSpeech, fontWeight = FontWeight.Bold)
            meaning.definitions.forEachIndexed { index, definition ->
                Text(text = "${index + 1}. ${definition.definition}")
                Spacer(modifier = Modifier.height(8.dp))
                definition.example?.let { example ->
                    Text(text = "Example: $example")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Source Urls:", fontWeight = FontWeight.Bold)

        wordInfo.sourceUrls.forEachIndexed { _, value ->
            val context = LocalContext.current
            val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(value)) }

            Text(text = value,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 4.dp)
                    .clickable {
                        context.startActivity(intent)
                    })
        }

        Spacer(modifier = Modifier.height(8.dp))

    }
}