package com.example.myaimodelswitcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class AiModel(val name: String, val provider: String, val description: String)
private data class ChatMessage(val fromUser: Boolean, val text: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MyAiApp() }
    }
}

@Composable
private fun MyAiApp() {
    val navy = Color(0xFF101827)
    val panel = Color(0xFF1B2738)
    val accent = Color(0xFF7C9DFF)

    val models = listOf(
        AiModel("GPT-4.1 mini", "OpenAI", "দ্রুত, সাধারণ কাজ"),
        AiModel("GPT-4.1", "OpenAI", "জটিল কাজ ও বিশ্লেষণ"),
        AiModel("Gemini 2.5 Flash", "Google", "দ্রুত মাল্টিমোডাল কাজ"),
        AiModel("Custom API", "Custom", "নিজস্ব OpenAI-compatible API")
    )
    var selected by remember { mutableStateOf(models.first()) }
    var showModels by remember { mutableStateOf(false) }
    var prompt by remember { mutableStateOf("") }
    var apiKey by remember { mutableStateOf("") }
    var endpoint by remember { mutableStateOf("https://api.openai.com/v1/chat/completions") }
    var showSettings by remember { mutableStateOf(false) }
    val messages = remember {
        mutableStateListOf(
            ChatMessage(false, "স্বাগতম! মডেল নির্বাচন করো। এটি এখন ডেমো সংস্করণ; আসল AI উত্তর পেতে নিরাপদ API সংযোগ দরকার।")
        )
    }

    MaterialTheme(colorScheme = darkColorScheme(primary = accent, background = navy, surface = panel)) {
        Surface(Modifier.fillMaxSize(), color = navy) {
            Column(Modifier.fillMaxSize().padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AutoAwesome, null, tint = accent, modifier = Modifier.size(34.dp))
                    Spacer(Modifier.width(10.dp))
                    Column(Modifier.weight(1f)) {
                        Text("My AI Switcher", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text("নিজস্ব AI কন্ট্রোল প্যানেল", color = Color.LightGray, fontSize = 12.sp)
                    }
                    IconButton(onClick = { showSettings = !showSettings }) {
                        Icon(Icons.Default.Settings, "সেটিংস")
                    }
                }
                Spacer(Modifier.height(18.dp))
                Text("ACTIVE MODEL", color = Color.LightGray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth().clickable { showModels = !showModels },
                    shape = RoundedCornerShape(16.dp), color = panel
                ) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Memory, null, tint = accent, modifier = Modifier.size(30.dp))
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(selected.name, fontWeight = FontWeight.SemiBold, fontSize = 17.sp)
                            Text("${selected.provider} • ${selected.description}", color = Color.LightGray, fontSize = 12.sp)
                        }
                        Icon(if (showModels) Icons.Default.ExpandLess else Icons.Default.ExpandMore, null)
                    }
                }
                if (showModels) {
                    Spacer(Modifier.height(8.dp))
                    models.forEach { model ->
                        Surface(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)
                                .clickable { selected = model; showModels = false },
                            color = if (selected == model) Color(0xFF293B58) else panel,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(model.name, Modifier.weight(1f))
                                if (selected == model) Icon(Icons.Default.CheckCircle, null, tint = accent)
                            }
                        }
                    }
                }
                if (showSettings) {
                    Spacer(Modifier.height(12.dp))
                    Text("API সেটিংস", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = endpoint, onValueChange = { endpoint = it },
                        label = { Text("API endpoint") }, modifier = Modifier.fillMaxWidth(), singleLine = true
                    )
                    OutlinedTextField(
                        value = apiKey, onValueChange = { apiKey = it },
                        label = { Text("API key — ডেমো সেটিং") },
                        modifier = Modifier.fillMaxWidth(), singleLine = true
                    )
                    Text(
                        "সতর্কতা: APK-তে API key রাখলে তা বের করা সম্ভব। অন্যদের জন্য প্রকাশের আগে নিরাপদ backend ব্যবহার করো।",
                        color = Color(0xFFFFC875), fontSize = 12.sp
                    )
                }
                Spacer(Modifier.height(14.dp))
                Text("CHAT  •  DEMO MODE", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.LightGray)
                Spacer(Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(messages) { msg ->
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = if (msg.fromUser) Arrangement.End else Arrangement.Start
                        ) {
                            Surface(
                                color = if (msg.fromUser) Color(0xFF304B76) else panel,
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier.widthIn(max = 300.dp)
                            ) {
                                Text(msg.text, Modifier.padding(12.dp), fontSize = 14.sp)
                            }
                        }
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = prompt, onValueChange = { prompt = it },
                        modifier = Modifier.weight(1f), placeholder = { Text("এখানে লিখো…") },
                        shape = RoundedCornerShape(18.dp), maxLines = 4
                    )
                    Spacer(Modifier.width(8.dp))
                    IconButton(onClick = {
                        if (prompt.isNotBlank()) {
                            messages.add(ChatMessage(true, prompt.trim()))
                            messages.add(ChatMessage(false, "এটি ডেমো উত্তর। প্রকৃত AI উত্তর পেতে API/backend সংযোগ যুক্ত করতে হবে।"))
                            prompt = ""
                        }
                    }) {
                        Icon(Icons.Default.Send, "পাঠাও", tint = accent)
                    }
                }
                Spacer(Modifier.height(6.dp))
                Text("মডেল তালিকা কাজ করে; প্রকৃত AI API এখনো যুক্ত নয়।", color = Color.Gray, fontSize = 10.sp)
            }
        }
    }
}
