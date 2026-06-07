package com.example

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import androidx.compose.ui.graphics.vector.ImageVector
import java.io.*
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import androidx.compose.ui.draw.drawBehind

val IosBlue = Color(0xFF8AA3FF)
val IosBackground = Color(0xFF07080C)
val IosCard = Color(0x2B1E243A)
val IosTextPrimary = Color(0xFFECEFFC)
val IosTextSecondary = Color(0xFFBAC1E1)
val IosBorder = Color(0x33FFFFFF)

@Composable
fun LiquidGlassBackgroundBox(
    viewModel: DiaryViewModel,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "LiquidGlass")
    val context = LocalContext.current
    
    val phase1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(25000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase1"
    )
    val phase2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(35000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase2"
    )
    val phase3 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(42000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase3"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF07080C))
    ) {
        if (viewModel.backgroundMode == "custom" && viewModel.customBackgroundExists) {
            AsyncImage(
                model = File(context.filesDir, "custom_background.jpg"),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = viewModel.backgroundOpacity
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    val w = size.width
                    val h = size.height
                    if (w > 0f && h > 0f) {
                        val x1 = w * (0.6f + 0.22f * Math.cos(phase1.toDouble()).toFloat())
                        val y1 = h * (0.35f + 0.15f * Math.sin(phase1.toDouble()).toFloat())
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFF1B2E5C).copy(alpha = 0.6f),
                                    Color(0xFF1B2E5C).copy(alpha = 0.15f),
                                    Color.Transparent
                                ),
                                center = androidx.compose.ui.geometry.Offset(x1, y1),
                                radius = w * 1.0f
                            ),
                            center = androidx.compose.ui.geometry.Offset(x1, y1),
                            radius = w * 1.0f
                        )

                        val x2 = w * (0.35f + 0.18f * Math.sin(phase2.toDouble()).toFloat())
                        val y2 = h * (0.7f + 0.18f * Math.cos(phase2.toDouble()).toFloat())
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFF11252D).copy(alpha = 0.55f),
                                    Color(0xFF11252D).copy(alpha = 0.12f),
                                    Color.Transparent
                                ),
                                center = androidx.compose.ui.geometry.Offset(x2, y2),
                                radius = w * 0.95f
                            ),
                            center = androidx.compose.ui.geometry.Offset(x2, y2),
                            radius = w * 0.95f
                        )

                        val x3 = w * (0.5f + 0.12f * Math.cos(phase3.toDouble()).toFloat())
                        val y3 = h * (0.5f + 0.12f * Math.sin(phase3.toDouble()).toFloat())
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFF2E193C).copy(alpha = 0.45f),
                                    Color(0xFF2E193C).copy(alpha = 0.08f),
                                    Color.Transparent
                                ),
                                center = androidx.compose.ui.geometry.Offset(x3, y3),
                                radius = w * 0.9f
                            ),
                            center = androidx.compose.ui.geometry.Offset(x3, y3),
                            radius = w * 0.9f
                        )
                    }
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.012f))
            ) {
                content()
            }
        }
    }
}

object SecurityHelper {
    private const val ALGORITHM = "AES/CBC/PKCS5Padding"
    
    private fun deriveKey(password: String): ByteArray {
        val digest = java.security.MessageDigest.getInstance("SHA-256")
        return digest.digest(password.toByteArray(Charsets.UTF_8))
    }
    
    fun encrypt(data: ByteArray, password: String): ByteArray {
        val keyBytes = deriveKey(password)
        val secretKey = SecretKeySpec(keyBytes, "AES")
        val cipher = Cipher.getInstance(ALGORITHM)
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv))
        val encrypted = cipher.doFinal(data)
        return iv + encrypted
    }
    
    fun decrypt(data: ByteArray, password: String): ByteArray {
        val keyBytes = deriveKey(password)
        val secretKey = SecretKeySpec(keyBytes, "AES")
        val cipher = Cipher.getInstance(ALGORITHM)
        val iv = data.copyOfRange(0, 16)
        val encrypted = data.copyOfRange(16, data.size)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
        return cipher.doFinal(encrypted)
    }
}

@Serializable
data class DiaryEntry(
    val id: String = UUID.randomUUID().toString(),
    val content: String,
    val dateMs: Long = System.currentTimeMillis(),
    val mood: String = "😊",
    val weather: String = "☀️",
    val imagePaths: List<String> = emptyList()
)

object StorageHelper {
    private const val DIARY_FILE_PLAIN = "diaries.json"
    private const val DIARY_FILE_ENCRYPTED = "diaries.bin"
    private const val IMAGES_DIR = "images"

    fun isEncrypted(context: Context): Boolean {
        return context.getFileStreamPath(DIARY_FILE_ENCRYPTED).exists()
    }
    
    fun savePlain(context: Context, entries: List<DiaryEntry>) {
        val jsonStr = Json.encodeToString(entries)
        context.openFileOutput(DIARY_FILE_PLAIN, Context.MODE_PRIVATE).use {
            it.write(jsonStr.toByteArray(Charsets.UTF_8))
        }
        val encFile = context.getFileStreamPath(DIARY_FILE_ENCRYPTED)
        if (encFile.exists()) encFile.delete()
    }
    
    fun loadPlain(context: Context): List<DiaryEntry> {
        val file = context.getFileStreamPath(DIARY_FILE_PLAIN)
        if (!file.exists()) return emptyList()
        return try {
            val jsonStr = file.readText(Charsets.UTF_8)
            Json.decodeFromString(jsonStr)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    fun saveEncrypted(context: Context, entries: List<DiaryEntry>, passcode: String) {
        val jsonStr = Json.encodeToString(entries)
        val encryptedData = SecurityHelper.encrypt(jsonStr.toByteArray(Charsets.UTF_8), passcode)
        context.openFileOutput(DIARY_FILE_ENCRYPTED, Context.MODE_PRIVATE).use {
            it.write(encryptedData)
        }
        val plainFile = context.getFileStreamPath(DIARY_FILE_PLAIN)
        if (plainFile.exists()) plainFile.delete()
    }
    
    fun loadEncrypted(context: Context, passcode: String): List<DiaryEntry>? {
        val file = context.getFileStreamPath(DIARY_FILE_ENCRYPTED)
        if (!file.exists()) return emptyList()
        return try {
            val encryptedData = file.readBytes()
            val decryptedData = SecurityHelper.decrypt(encryptedData, passcode)
            val jsonStr = String(decryptedData, Charsets.UTF_8)
            Json.decodeFromString(jsonStr)
        } catch (e: Exception) {
            null
        }
    }
    
    fun saveImage(context: Context, uri: Uri, passcode: String?): String {
        val imagesDir = File(context.filesDir, IMAGES_DIR)
        if (!imagesDir.exists()) imagesDir.mkdirs()
        val filename = "img_${System.currentTimeMillis()}_${UUID.randomUUID().toString().take(6)}.bin"
        val destFile = File(imagesDir, filename)
        
        try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val bytes = inputStream.readBytes()
                if (passcode != null) {
                    val encrypted = SecurityHelper.encrypt(bytes, passcode)
                    destFile.writeBytes(encrypted)
                } else {
                    destFile.writeBytes(bytes)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return filename
    }
    
    fun loadImageBytes(context: Context, filename: String, passcode: String?): ByteArray? {
        val file = File(File(context.filesDir, IMAGES_DIR), filename)
        if (!file.exists()) return null
        return try {
            val bytes = file.readBytes()
            if (passcode != null) {
                SecurityHelper.decrypt(bytes, passcode)
            } else {
                bytes
            }
        } catch (e: Exception) {
            null
        }
    }
    
    fun deleteImage(context: Context, filename: String) {
        val file = File(File(context.filesDir, IMAGES_DIR), filename)
        if (file.exists()) file.delete()
    }

    fun exportBackup(context: Context, outStream: OutputStream) {
        exportBackupWithOptions(context, outStream, null)
    }

    fun exportBackupWithOptions(
        context: Context,
        outStream: OutputStream,
        selectedIds: List<String>?
    ) {
        ZipOutputStream(outStream).use { zos ->
            val allEntries = loadPlain(context)
            val entriesToExport = if (selectedIds == null) {
                allEntries
            } else {
                allEntries.filter { it.id in selectedIds }
            }

            zos.putNextEntry(ZipEntry(DIARY_FILE_PLAIN))
            val jsonStr = Json.encodeToString(entriesToExport)
            zos.write(jsonStr.toByteArray(Charsets.UTF_8))
            zos.closeEntry()

            val imagesToExport = entriesToExport.flatMap { it.imagePaths }.toSet()
            val imagesDir = File(context.filesDir, IMAGES_DIR)
            if (imagesDir.exists() && imagesDir.isDirectory && imagesToExport.isNotEmpty()) {
                imagesDir.listFiles()?.forEach { file ->
                    if (file.name in imagesToExport) {
                        zos.putNextEntry(ZipEntry("$IMAGES_DIR/${file.name}"))
                        zos.write(file.readBytes())
                        zos.closeEntry()
                    }
                }
            }
        }
    }
    
    fun importBackup(context: Context, inStream: InputStream): Boolean {
        return importBackupWithOptions(context, inStream, overwrite = true)
    }
    
    fun importBackupWithOptions(context: Context, inStream: InputStream, overwrite: Boolean): Boolean {
        return try {
            ZipInputStream(inStream).use { zis ->
                var entry = zis.nextEntry
                val tempJsonBytes = ByteArrayOutputStream()
                val imagesDir = File(context.filesDir, IMAGES_DIR)
                if (!imagesDir.exists()) imagesDir.mkdirs()
                
                if (overwrite) {
                    imagesDir.listFiles()?.forEach { file ->
                        file.delete()
                    }
                }
                
                while (entry != null) {
                    if (entry.name == DIARY_FILE_PLAIN) {
                        zis.copyTo(tempJsonBytes)
                    } else if (entry.name.startsWith("$IMAGES_DIR/")) {
                        val filename = entry.name.substring(IMAGES_DIR.length + 1)
                        if (filename.isNotEmpty()) {
                            val destFile = File(imagesDir, filename)
                            destFile.outputStream().use { fos ->
                                zis.copyTo(fos)
                            }
                        }
                    }
                    entry = zis.nextEntry
                }
                
                if (tempJsonBytes.size() > 0) {
                    val importedJsonBytes = tempJsonBytes.toByteArray()
                    val plainFile = context.getFileStreamPath(DIARY_FILE_PLAIN)
                    
                    if (overwrite) {
                        plainFile.writeBytes(importedJsonBytes)
                    } else {
                        val importedJsonStr = String(importedJsonBytes, Charsets.UTF_8)
                        val importedEntries = try {
                            Json.decodeFromString<List<DiaryEntry>>(importedJsonStr)
                        } catch (e: Exception) {
                            emptyList()
                        }
                        
                        val existingEntries = loadPlain(context)
                        val merged = (importedEntries + existingEntries).distinctBy { it.id }
                        val jsonStr = Json.encodeToString(merged)
                        plainFile.writeBytes(jsonStr.toByteArray(Charsets.UTF_8))
                    }
                    true
                } else {
                    false
                }
            }
        } catch (e: Exception) {
            false
        }
    }
}

class DiaryViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    
    private val _entries = MutableStateFlow<List<DiaryEntry>>(emptyList())
    val entries: StateFlow<List<DiaryEntry>> = _entries.asStateFlow()
    
    var isEncrypted by mutableStateOf(StorageHelper.isEncrypted(context))
        private set
        
    var isUnlocked by mutableStateOf(!isEncrypted)
        private set
        
    var currentPassword by mutableStateOf<String?>(null)
        private set

    private val prefs = context.getSharedPreferences("diary_settings", Context.MODE_PRIVATE)
    
    var backgroundMode by mutableStateOf(prefs.getString("bg_mode", "default") ?: "default")
        private set
        
    var backgroundOpacity by mutableStateOf(prefs.getFloat("bg_opacity", 0.3f))
        private set

    var customBackgroundExists by mutableStateOf(File(context.filesDir, "custom_background.jpg").exists())
        private set

    fun updateBackgroundMode(mode: String) {
        backgroundMode = mode
        prefs.edit().putString("bg_mode", mode).apply()
    }

    fun updateBackgroundOpacity(opacity: Float) {
        backgroundOpacity = opacity
        prefs.edit().putFloat("bg_opacity", opacity).apply()
    }

    fun setCustomBackground(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val destFile = File(context.filesDir, "custom_background.jpg")
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    destFile.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                withContext(Dispatchers.Main) {
                    customBackgroundExists = destFile.exists()
                    if (backgroundMode == "default") {
                        updateBackgroundMode("custom")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    var draftMood by mutableStateOf("😊")
    var draftWeather by mutableStateOf("☀️")
    var draftDateMs by mutableStateOf(System.currentTimeMillis())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (!isEncrypted) {
                val loaded = StorageHelper.loadPlain(context).sortedByDescending { it.dateMs }
                withContext(Dispatchers.Main) {
                    _entries.value = loaded
                }
            }
        }
    }
    
    suspend fun unlock(password: String): Boolean = withContext(Dispatchers.IO) {
        val loaded = StorageHelper.loadEncrypted(context, password)
        if (loaded != null) {
            withContext(Dispatchers.Main) {
                currentPassword = password
                isUnlocked = true
                _entries.value = loaded.sortedByDescending { it.dateMs }
            }
            true
        } else {
            false
        }
    }
    
    fun setupEncryption(password: String) {
        currentPassword = password
        isEncrypted = true
        isUnlocked = true
        viewModelScope.launch(Dispatchers.IO) {
            StorageHelper.saveEncrypted(context, _entries.value, password)
        }
    }
    
    fun removeEncryption() {
        val list = _entries.value
        isEncrypted = false
        isUnlocked = true
        currentPassword = null
        viewModelScope.launch(Dispatchers.IO) {
            context.getFileStreamPath("diaries.bin").delete()
            StorageHelper.savePlain(context, list)
        }
    }
    
    fun addEntry(content: String, imageUris: List<Uri>, customId: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val copiedImagePaths = imageUris.map { uri ->
                StorageHelper.saveImage(context, uri, currentPassword)
            }
            withContext(Dispatchers.Main) {
                val updatedList = _entries.value.toMutableList()
                val existingIndex = customId?.let { id -> updatedList.indexOfFirst { it.id == id } } ?: -1
                
                if (existingIndex != -1) {
                    val oldEntry = updatedList[existingIndex]
                    val mergedImages = oldEntry.imagePaths + copiedImagePaths
                    updatedList[existingIndex] = oldEntry.copy(
                        content = content,
                        imagePaths = mergedImages
                    )
                } else {
                    val newEntry = DiaryEntry(
                        content = content,
                        mood = draftMood,
                        weather = draftWeather,
                        dateMs = draftDateMs,
                        imagePaths = copiedImagePaths
                    )
                    updatedList.add(0, newEntry)
                }
                
                _entries.value = updatedList.sortedByDescending { it.dateMs }
                launch(Dispatchers.IO) {
                    saveData()
                }
            }
        }
    }
    
    fun deleteEntry(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val entry = _entries.value.find { it.id == id }
            entry?.imagePaths?.forEach { path ->
                StorageHelper.deleteImage(context, path)
            }
            withContext(Dispatchers.Main) {
                _entries.value = _entries.value.filter { it.id != id }
                launch(Dispatchers.IO) {
                    saveData()
                }
            }
        }
    }
    
    private suspend fun saveData() = withContext(Dispatchers.IO) {
        val pass = currentPassword
        if (isEncrypted && pass != null) {
            StorageHelper.saveEncrypted(context, _entries.value, pass)
        } else {
            StorageHelper.savePlain(context, _entries.value)
        }
    }
    
    suspend fun exportBackupFile(outputStream: OutputStream, selectedIds: List<String>? = null) = withContext(Dispatchers.IO) {
        StorageHelper.exportBackupWithOptions(context, outputStream, selectedIds)
    }
    
    suspend fun importBackupFile(inputStream: InputStream, overwrite: Boolean = true) = withContext(Dispatchers.IO) {
        val success = StorageHelper.importBackupWithOptions(context, inputStream, overwrite)
        if (success) {
            val loaded = StorageHelper.loadPlain(context).sortedByDescending { it.dateMs }
            withContext(Dispatchers.Main) {
                _entries.value = loaded
            }
        }
        success
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: DiaryViewModel = viewModel()
            val obsidianDarkColors = darkColorScheme(
                primary = IosBlue,
                background = IosBackground,
                surface = IosCard,
                onPrimary = Color.Black,
                onBackground = IosTextPrimary,
                onSurface = IosTextPrimary,
                onSurfaceVariant = IosTextSecondary
            )
            MaterialTheme(
                colorScheme = obsidianDarkColors
            ) {
                MainAppShell(viewModel)
            }
        }
    }
}

@Composable
fun MainAppShell(viewModel: DiaryViewModel) {
    LiquidGlassBackgroundBox(viewModel = viewModel, modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent
        ) {
            if (viewModel.isEncrypted && !viewModel.isUnlocked) {
                LockScreen(viewModel)
            } else {
                MainAppNavigation(viewModel)
            }
        }
    }
}

@Composable
fun WelcomeScreen(navController: NavController) {
    val scale = remember { Animatable(0.95f) }
    val alpha = remember { Animatable(0f) }
    
    LaunchedEffect(Unit) {
        launch {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(700)
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(24.dp)
                .widthIn(max = 480.dp)
                .graphicsLayer {
                    this.alpha = alpha.value
                    this.scaleX = scale.value
                    this.scaleY = scale.value
                }
        ) {
            Box(
                modifier = Modifier
                    .size(112.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.White.copy(alpha = 0.07f))
                    .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.16f)), RoundedCornerShape(32.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("🌸", fontSize = 52.sp)
            }
            
            Spacer(Modifier.height(36.dp))
            
            Text(
                "hi，欢迎回来",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = IosTextPrimary,
                textAlign = TextAlign.Center
            )
            
            Spacer(Modifier.height(16.dp))
            
            Text(
                "用日记记录下重要的瞬间吧！",
                fontSize = 16.sp,
                color = IosTextSecondary,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            
            Spacer(Modifier.height(56.dp))
            
            IosButton(
                onClick = {
                    navController.navigate("home") {
                        popUpTo("welcome") { inclusive = true }
                    }
                },
                text = "现在开始吧！",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun LockScreen(viewModel: DiaryViewModel) {
    var passcodeEntered by remember { mutableStateOf("") }
    var errorShake by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    val shakeOffset = remember { Animatable(0f) }
    
    LaunchedEffect(errorShake) {
        if (errorShake) {
            val spec = spring<Float>(stiffness = Spring.StiffnessHigh, dampingRatio = Spring.DampingRatioHighBouncy)
            shakeOffset.animateTo(20f, spec)
            shakeOffset.animateTo(-20f, spec)
            shakeOffset.animateTo(10f, spec)
            shakeOffset.animateTo(-10f, spec)
            shakeOffset.animateTo(0f, spec)
            errorShake = false
            passcodeEntered = ""
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 48.dp)
                .widthIn(max = 400.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(Modifier.height(48.dp))
                Icon(
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = "Lock",
                    tint = IosBlue,
                    modifier = Modifier.size(56.dp)
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    "输入密码解锁",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = IosTextPrimary
                )
                Spacer(Modifier.height(24.dp))
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.graphicsLayer { translationX = shakeOffset.value }
                ) {
                    for (i in 0 until 4) {
                        val active = i < passcodeEntered.length
                        val scaleState by animateFloatAsState(
                            targetValue = if (active) 1.2f else 1.0f,
                            animationSpec = spring(stiffness = Spring.StiffnessMedium)
                        )
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .scale(scaleState)
                                .clip(CircleShape)
                                .background(
                                    if (active) IosBlue else Color.White.copy(alpha = 0.2f)
                                )
                        )
                    }
                }
            }
            
            Column(
                modifier = Modifier.padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val padData = listOf(
                    listOf("1", "2", "3"),
                    listOf("4", "5", "6"),
                    listOf("7", "8", "9"),
                    listOf("C", "0", "⌫")
                )
                
                padData.forEach { row ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        row.forEach { char ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1.2f)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(
                                        if (char.isEmpty()) Color.Transparent else Color.White.copy(alpha = 0.08f)
                                    )
                                    .then(
                                        if (char.isNotEmpty()) {
                                            Modifier.border(
                                                BorderStroke(1.dp, Color.White.copy(alpha = 0.12f)),
                                                RoundedCornerShape(16.dp)
                                            )
                                        } else Modifier
                                    )
                                    .clickable(enabled = char.isNotEmpty()) {
                                        when (char) {
                                            "C" -> passcodeEntered = ""
                                            "⌫" -> {
                                                if (passcodeEntered.isNotEmpty()) {
                                                    passcodeEntered = passcodeEntered.dropLast(1)
                                                }
                                            }
                                            else -> {
                                                if (passcodeEntered.length < 4) {
                                                    passcodeEntered += char
                                                    if (passcodeEntered.length == 4) {
                                                        scope.launch {
                                                            val ok = viewModel.unlock(passcodeEntered)
                                                            if (!ok) {
                                                                errorShake = true
                                                                Toast.makeText(context, "密码错误", Toast.LENGTH_SHORT).show()
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = char,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = IosTextPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IosButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    color: Color = IosBlue,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            disabledContainerColor = color.copy(alpha = 0.3f),
            disabledContentColor = Color.White.copy(alpha = 0.4f)
        ),
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
        modifier = modifier.height(52.dp)
    ) {
        Text(
            text = text,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = if (enabled) Color.White else Color.White.copy(alpha = 0.4f)
        )
    }
}

@Composable
fun MainAppNavigation(viewModel: DiaryViewModel) {
    val navController = rememberNavController()
    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            NavigationBar(
                containerColor = Color.Transparent,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0x7A090B14)
                            )
                        )
                    )
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        drawLine(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    IosBorder,
                                    Color.Transparent
                                )
                            ),
                            start = androidx.compose.ui.geometry.Offset(0f, 0f),
                            end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                            strokeWidth = strokeWidth
                        )
                    }
                    .navigationBarsPadding()
            ) {
                val navBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(null)
                val currentRoute = navBackStackEntry?.destination?.route ?: "welcome"
                
                NavigationBarItem(
                    selected = currentRoute == "home" || currentRoute == "welcome",
                    onClick = {
                        if (currentRoute != "home") {
                            navController.navigate("home") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    },
                    icon = { Icon(Icons.Rounded.Book, "Diary") },
                    label = { Text("日记", fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = IosBlue,
                        selectedTextColor = IosBlue,
                        unselectedIconColor = IosTextSecondary,
                        unselectedTextColor = IosTextSecondary,
                        indicatorColor = IosBlue.copy(alpha = 0.08f)
                    )
                )
                NavigationBarItem(
                    selected = currentRoute == "profile",
                    onClick = {
                        if (currentRoute != "profile") {
                            navController.navigate("profile")
                        }
                    },
                    icon = { Icon(Icons.Rounded.Person, "Setting") },
                    label = { Text("设置", fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = IosBlue,
                        selectedTextColor = IosBlue,
                        unselectedIconColor = IosTextSecondary,
                        unselectedTextColor = IosTextSecondary,
                        indicatorColor = IosBlue.copy(alpha = 0.08f)
                    )
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController, 
            startDestination = "welcome", 
            modifier = Modifier.padding(padding),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow, dampingRatio = Spring.DampingRatioNoBouncy)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it / 3 },
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow, dampingRatio = Spring.DampingRatioNoBouncy)
                ) + fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it / 3 },
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow, dampingRatio = Spring.DampingRatioNoBouncy)
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow, dampingRatio = Spring.DampingRatioNoBouncy)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            composable("welcome") { WelcomeScreen(navController) }
            composable("home") { HomeScreen(viewModel, navController) }
            composable("profile") { ProfileScreen(viewModel) }
            composable("editor?id={id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                DiaryEditorScreen(viewModel, navController, id)
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: DiaryViewModel, navController: NavController) {
    val entries by viewModel.entries.collectAsState()
    var searchKeyword by remember { mutableStateOf("") }
    var showMetaSelector by remember { mutableStateOf(false) }
    var activePreviewImage by remember { mutableStateOf<String?>(null) }
    
    val todayMs = System.currentTimeMillis()
    val hasToday = entries.any { isSameDay(it.dateMs, todayMs) }
    
    val filteredEntries = remember(entries, searchKeyword) {
        if (searchKeyword.isBlank()) {
            entries
        } else {
            entries.filter { it.content.contains(searchKeyword, ignoreCase = true) }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .align(Alignment.TopCenter)
                .widthIn(max = 600.dp)
        ) {
            Text(
                "Daily/日记", 
                fontSize = 32.sp, 
                fontWeight = FontWeight.Bold, 
                color = IosTextPrimary,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
            )

            OutlinedTextField(
                value = searchKeyword,
                onValueChange = { searchKeyword = it },
                placeholder = { Text("搜索我的记忆...", color = IosTextSecondary) },
                leadingIcon = { Icon(Icons.Rounded.Search, "Search", tint = IosTextSecondary) },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = IosBlue,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.04f),
                    focusedContainerColor = Color.White.copy(alpha = 0.08f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.04f),
                    focusedTextColor = IosTextPrimary,
                    unfocusedTextColor = IosTextPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 16.dp)
            )
            
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    TodayProgressCard(hasToday = hasToday) {
                        showMetaSelector = true
                    }
                }
                
                if (filteredEntries.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("✍️", fontSize = 48.sp)
                                Spacer(Modifier.height(16.dp))
                                Text(
                                    if (searchKeyword.isNotBlank()) "没有检索到相关日记" else "记录一下宝贵的记忆吧",
                                    fontSize = 15.sp,
                                    color = IosTextSecondary,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                } else {
                    items(filteredEntries, key = { it.id }) { entry ->
                        DiaryCard(
                            entry = entry,
                            password = viewModel.currentPassword,
                            onClick = {
                                navController.navigate("editor?id=${entry.id}")
                            },
                            onImageClick = { path ->
                                activePreviewImage = path
                            }
                        )
                    }
                }
            }
        }
        
        FloatingActionButton(
            onClick = { showMetaSelector = true },
            containerColor = IosBlue,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 24.dp)
                .size(60.dp)
        ) {
            Icon(Icons.Filled.Add, "New Entry", tint = Color.White, modifier = Modifier.size(32.dp))
        }

        if (showMetaSelector) {
            DiaryMetadataSelector(
                onDismiss = { showMetaSelector = false },
                onConfirm = { mood, weather ->
                    viewModel.draftMood = mood
                    viewModel.draftWeather = weather
                    viewModel.draftDateMs = System.currentTimeMillis()
                    showMetaSelector = false
                    navController.navigate("editor")
                }
            )
        }

        if (activePreviewImage != null) {
            LightboxDialog(
                model = activePreviewImage,
                password = viewModel.currentPassword,
                onDismiss = { activePreviewImage = null }
            )
        }
    }
}

@Composable
fun TodayProgressCard(hasToday: Boolean, onClick: () -> Unit) {
    if (hasToday) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0x1C2EAF5F))
                .border(BorderStroke(1.dp, Color(0x402EAF5F)), RoundedCornerShape(24.dp))
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.CheckCircle, "Done", tint = Color(0xFF6BFF9A), modifier = Modifier.size(28.dp))
            Spacer(Modifier.width(12.dp))
            Text("今日记录已完成！记忆点拓展完成", color = Color(0xFF9EFFBA), fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
    } else {
        val infiniteTransition = rememberInfiniteTransition(label = "pulse")
        val pulseScale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.02f,
            animationSpec = infiniteRepeatable(
                animation = tween(1200, easing = EaseInOutBack),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulseScale"
        )
        
        Surface(
            onClick = onClick,
            shape = RoundedCornerShape(28.dp),
            color = IosBlue.copy(alpha = 0.12f),
            border = BorderStroke(1.dp, IosBlue.copy(alpha = 0.25f)),
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = pulseScale
                    scaleY = pulseScale
                }
                .padding(horizontal = 24.dp)
        ) {
            Row(
                modifier = Modifier.padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(IosBlue.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Rounded.EditCalendar, "Edit", tint = IosBlue)
                }
                Spacer(Modifier.width(16.dp))
                Column {
                    Text("今日还未记录", fontWeight = FontWeight.Bold, color = IosBlue, fontSize = 18.sp)
                    Text("记录一下宝贵的记忆吧", fontSize = 14.sp, color = IosBlue.copy(alpha=0.8f))
                }
            }
        }
    }
}

@Composable
fun DiaryMetadataSelector(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var step by remember { mutableStateOf(1) }
    var mood by remember { mutableStateOf("😊") }
    var weather by remember { mutableStateOf("☀️") }
    
    val emojis = listOf("😊", "🥰", "😢", "😡", "😴", "🤪", "🥳", "🤔")
    val weathers = listOf("☀️", "⛅", "☁️", "🌧️", "❄️", "⚡", "💨", "🌫️")

    var offsetY by remember { mutableStateOf(400.dp) }
    val animatedOffsetY by animateDpAsState(
        targetValue = offsetY,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessMediumLow),
        label = "offset"
    )
    LaunchedEffect(Unit) {
        offsetY = 0.dp
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onDismiss() },
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = Color(0xFF101322),
                border = BorderStroke(1.dp, IosBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 500.dp)
                    .offset(y = animatedOffsetY)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessMediumLow
                        )
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { }
            ) {
                Column(
                    modifier = Modifier
                        .padding(28.dp)
                        .navigationBarsPadding()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "建立今日印象",
                            fontSize = 16.sp,
                            color = IosTextSecondary,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Rounded.Close, "Dismiss", tint = IosTextSecondary, modifier = Modifier.size(20.dp))
                        }
                    }
                    
                    Spacer(Modifier.height(16.dp))
                    
                    AnimatedContent(
                        targetState = step,
                        label = "step_animation",
                        transitionSpec = {
                            if (targetState > initialState) {
                                (slideInHorizontally { width -> width / 2 } + fadeIn(animationSpec = tween(250))).togetherWith(
                                    slideOutHorizontally { width -> -width / 2 } + fadeOut(animationSpec = tween(250))
                                )
                            } else {
                                (slideInHorizontally { width -> -width / 2 } + fadeIn(animationSpec = tween(250))).togetherWith(
                                    slideOutHorizontally { width -> width / 2 } + fadeOut(animationSpec = tween(250))
                                )
                            }
                        }
                    ) { currentStep ->
                        if (currentStep == 1) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text("心情如何？", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = IosTextPrimary)
                                Spacer(Modifier.height(16.dp))
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    contentPadding = PaddingValues(bottom = 8.dp)
                                ) {
                                    items(emojis) { e ->
                                        Box(
                                            modifier = Modifier
                                                .size(56.dp)
                                                .clip(CircleShape)
                                                .background(
                                                    if (mood == e) IosBlue.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.05f)
                                                )
                                                .border(
                                                    BorderStroke(
                                                        if (mood == e) 2.dp else 1.dp,
                                                        if (mood == e) IosBlue else Color.White.copy(alpha = 0.12f)
                                                    ),
                                                    CircleShape
                                                )
                                                .clickable { mood = e },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(e, fontSize = 28.sp)
                                        }
                                    }
                                }
                                
                                Spacer(Modifier.height(28.dp))
                                Text("天气如何？", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = IosTextPrimary)
                                Spacer(Modifier.height(16.dp))
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    contentPadding = PaddingValues(bottom = 8.dp)
                                ) {
                                    items(weathers) { w ->
                                        Box(
                                            modifier = Modifier
                                                .size(56.dp)
                                                .clip(CircleShape)
                                                .background(
                                                    if (weather == w) IosBlue.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.05f)
                                                )
                                                .border(
                                                    BorderStroke(
                                                        if (weather == w) 2.dp else 1.dp,
                                                        if (weather == w) IosBlue else Color.White.copy(alpha = 0.12f)
                                                    ),
                                                    CircleShape
                                                )
                                                .clickable { weather = w },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(w, fontSize = 28.sp)
                                        }
                                    }
                                }
                                
                                Spacer(Modifier.height(36.dp))
                                IosButton(
                                    onClick = { step = 2 },
                                    text = "下一步",
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        } else {
                            val arrowTransition = rememberInfiniteTransition(label = "arrow_nudge")
                            val arrowOffsetX by arrowTransition.animateFloat(
                                initialValue = -4f,
                                targetValue = 12f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(650, easing = EaseInOut),
                                    repeatMode = RepeatMode.Reverse
                                ),
                                label = "arrowOffsetX"
                            )
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Spacer(Modifier.height(16.dp))
                                Box(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(CircleShape)
                                        .background(
                                            Brush.sweepGradient(
                                                listOf(
                                                    IosBlue.copy(alpha = 0.35f),
                                                    Color(0xFF2E193C).copy(alpha = 0.35f),
                                                    IosBlue.copy(alpha = 0.35f)
                                                )
                                            )
                                        )
                                        .border(BorderStroke(2.dp, IosBlue), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.ArrowForward,
                                        contentDescription = "Forward Animation",
                                        tint = IosBlue,
                                        modifier = Modifier
                                            .size(42.dp)
                                            .offset(x = arrowOffsetX.dp)
                                    )
                                }
                                Spacer(Modifier.height(28.dp))
                                Text("现在开始吧！", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = IosTextPrimary)
                                Spacer(Modifier.height(12.dp))
                                Text(
                                    "一切都已就绪，写下属于你自己的记忆吧", 
                                    fontSize = 15.sp, 
                                    color = IosTextSecondary, 
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                                Spacer(Modifier.height(36.dp))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    TextButton(
                                        onClick = { step = 1 },
                                        shape = RoundedCornerShape(16.dp),
                                        modifier = Modifier
                                            .weight(1f)
                                            .heightIn(min = 52.dp)
                                            .border(BorderStroke(1.dp, IosBorder), RoundedCornerShape(16.dp))
                                    ) {
                                        Text("上一步", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = IosBlue)
                                    }
                                    IosButton(
                                        onClick = { onConfirm(mood, weather) },
                                        text = "记录记忆",
                                        modifier = Modifier.weight(1.5f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun decodeSampledBitmapFromByteArray(data: ByteArray, reqWidth: Int, reqHeight: Int): Bitmap? {
    return try {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeByteArray(data, 0, data.size, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        BitmapFactory.decodeByteArray(data, 0, data.size, options)
    } catch (e: Exception) {
        null
    }
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1
    if (height > reqHeight || width > reqWidth) {
        val halfHeight = height / 2
        val halfWidth = width / 2
        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}

@Composable
fun SecureImage(
    path: String,
    password: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    var bitmap by remember(path, password) { mutableStateOf<Bitmap?>(null) }
    var isLoading by remember(path, password) { mutableStateOf(true) }
    
    LaunchedEffect(path, password) {
        withContext(Dispatchers.IO) {
            val bytes = StorageHelper.loadImageBytes(context, path, password)
            if (bytes != null) {
                bitmap = decodeSampledBitmapFromByteArray(bytes, 400, 400)
            }
            isLoading = false
        }
    }
    
    Box(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.04f))
            .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.08f)), RoundedCornerShape(16.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = IosBlue, strokeWidth = 2.dp, modifier = Modifier.size(24.dp))
        } else {
            val bmp = bitmap
            if (bmp != null) {
                Image(
                    bitmap = bmp.asImageBitmap(),
                    contentDescription = "Secure Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text("⚠️", fontSize = 24.sp)
            }
        }
    }
}

@Composable
fun LightboxDialog(
    model: Any?, 
    password: String?,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var bitmap by remember(model, password) { mutableStateOf<Bitmap?>(null) }
    var isLoading by remember(model, password) { mutableStateOf(model is String) }
    
    LaunchedEffect(model, password) {
        if (model is String) {
            withContext(Dispatchers.IO) {
                val bytes = StorageHelper.loadImageBytes(context, model, password)
                if (bytes != null) {
                    bitmap = decodeSampledBitmapFromByteArray(bytes, 1080, 1920)
                }
                isLoading = false
            }
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.95f))
                .clickable { onDismiss() }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap!!.asImageBitmap(),
                            contentDescription = "Preview Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f, false),
                            contentScale = ContentScale.Fit
                        )
                    } else if (model is Uri) {
                        AsyncImage(
                            model = model,
                            contentDescription = "Preview Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f, false),
                            contentScale = ContentScale.Fit
                        )
                    } else {
                        Text("⚠️ 下载图片失败", color = Color.White, fontSize = 20.sp)
                    }
                }
            }
            
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(16.dp)
                    .align(Alignment.TopEnd)
            ) {
                Icon(Icons.Rounded.Close, "Close", tint = Color.White, modifier = Modifier.size(32.dp))
            }
        }
    }
}

@Composable
fun DiaryCard(
    entry: DiaryEntry,
    password: String?,
    onClick: () -> Unit,
    onImageClick: (String) -> Unit
) {
    var revealed by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        revealed = true
    }
    
    AnimatedVisibility(
        visible = revealed,
        enter = slideInVertically(
            initialOffsetY = { 50 },
            animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow)
        ) + fadeIn(animationSpec = tween(500)),
        exit = fadeOut(),
        modifier = Modifier.fillMaxWidth()
    ) {
        Surface(
            onClick = onClick,
            shape = RoundedCornerShape(28.dp),
            color = IosCard,
            border = BorderStroke(1.dp, IosBorder),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val df = SimpleDateFormat("MM月dd日", Locale.getDefault())
                    Text(
                        text = df.format(Date(entry.dateMs)),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = IosTextPrimary
                    )
                    
                    Surface(
                        color = Color.White.copy(alpha = 0.06f),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.12f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "${entry.mood} ${entry.weather}",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = IosTextPrimary
                        )
                    }
                }
                
                Spacer(Modifier.height(14.dp))
                
                Text(
                    text = entry.content,
                    fontSize = 16.sp,
                    color = IosTextPrimary,
                    lineHeight = 24.sp,
                    maxLines = 4
                )
                
                if (entry.imagePaths.isNotEmpty()) {
                    Spacer(Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val count = entry.imagePaths.size.coerceAtMost(3)
                        for (i in 0 until count) {
                            SecureImage(
                                path = entry.imagePaths[i],
                                password = password,
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1.2f)
                                    .clip(RoundedCornerShape(16.dp)),
                                onClick = { onImageClick(entry.imagePaths[i]) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(viewModel: DiaryViewModel) {
    var showPasswordDialog by remember { mutableStateOf(false) }
    var showExportOptions by remember { mutableStateOf(false) }
    var showImportOptions by remember { mutableStateOf(false) }
    
    var exportSelectedIds by remember { mutableStateOf<List<String>?>(null) }
    var importOverwriteMode by remember { mutableStateOf(false) }

    val entries by viewModel.entries.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/zip"),
        onResult = { uri: Uri? ->
            if (uri != null) {
                scope.launch {
                    try {
                        context.contentResolver.openOutputStream(uri)?.use { os ->
                            viewModel.exportBackupFile(os, exportSelectedIds)
                        }
                        Toast.makeText(context, "导出备份成功", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "导出备份失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    )
    
    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                scope.launch {
                    try {
                        context.contentResolver.openInputStream(uri)?.use { isStream ->
                            val success = viewModel.importBackupFile(isStream, importOverwriteMode)
                            if (success) {
                                Toast.makeText(context, "导入备份成功", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "导入备份失败, 请确保文件正确", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "导入备份失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    )

    val backgroundPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                viewModel.setCustomBackground(uri)
            }
        }
    )

    Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .align(Alignment.TopCenter)
                .widthIn(max = 600.dp)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 60.dp)
        ) {
            Text(
                "设置", 
                fontSize = 32.sp, 
                fontWeight = FontWeight.Bold, 
                color = IosTextPrimary,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)
            )
            
            Surface(
                color = IosCard,
                shape = RoundedCornerShape(28.dp),
                border = BorderStroke(1.dp, IosBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(IosBlue.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🧑‍💻", fontSize = 32.sp)
                    }
                    Spacer(Modifier.width(20.dp))
                    Column {
                        Text("LSQ", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = IosTextPrimary)
                        Spacer(Modifier.height(4.dp))
                        Text("软件开发者", fontSize = 14.sp, color = IosTextSecondary, fontWeight = FontWeight.Medium)
                    }
                }
            }
            
            Spacer(Modifier.height(16.dp))
            
            Text(
                "数据与安全", 
                fontSize = 14.sp, 
                fontWeight = FontWeight.Bold, 
                color = IosTextSecondary, 
                modifier = Modifier.padding(horizontal = 36.dp, vertical = 8.dp)
            )
            
            Surface(
                color = IosCard, 
                shape = RoundedCornerShape(28.dp),
                border = BorderStroke(1.dp, IosBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Column(Modifier.padding(vertical = 8.dp)) {
                    SettingsItem(
                        title = "日记安全加密", 
                        subtitle = "我们始终认为隐私是一项基本人权", 
                        onClick = {
                            if (viewModel.isEncrypted) {
                                viewModel.removeEncryption()
                            } else {
                                showPasswordDialog = true
                            }
                        }, 
                        trailing = {
                            Switch(
                                checked = viewModel.isEncrypted, 
                                onCheckedChange = { 
                                    if (it) showPasswordDialog = true else viewModel.removeEncryption() 
                                }, 
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White, 
                                    checkedTrackColor = IosBlue
                                )
                            )
                        }
                    )
                    HorizontalDivider(color = IosBackground, modifier = Modifier.padding(horizontal = 24.dp))
                    SettingsItem(
                        title = "导出日记", 
                        subtitle = "备份全部或选择性数据", 
                        disabled = viewModel.isEncrypted, 
                        onClick = { showExportOptions = true }, 
                        icon = if (viewModel.isEncrypted) Icons.Filled.Lock else Icons.Filled.Upload
                    )
                    HorizontalDivider(color = IosBackground, modifier = Modifier.padding(horizontal = 24.dp))
                    SettingsItem(
                        title = "导入日记", 
                        subtitle = "恢复备份数据，支持覆盖或合并", 
                        disabled = viewModel.isEncrypted, 
                        onClick = { showImportOptions = true }, 
                        icon = if (viewModel.isEncrypted) Icons.Filled.Lock else Icons.Filled.Download
                    )
                }
            }
            
            Text(
                "※ 加密开启时数据成为密文，不可使用导入与导出功能。", 
                fontSize = 12.sp, 
                color = IosTextSecondary, 
                modifier = Modifier.padding(horizontal = 36.dp, vertical = 8.dp)
            )
            
            Spacer(Modifier.height(16.dp))

            Text(
                "个性化背景", 
                fontSize = 14.sp, 
                fontWeight = FontWeight.Bold, 
                color = IosTextSecondary, 
                modifier = Modifier.padding(horizontal = 36.dp, vertical = 8.dp)
            )
            
            Surface(
                color = IosCard, 
                shape = RoundedCornerShape(28.dp),
                border = BorderStroke(1.dp, IosBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Column(Modifier.padding(vertical = 16.dp, horizontal = 24.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("主题背景模式", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = IosTextPrimary)
                            Text("可选择默认宇宙深色或自定义精美照片", fontSize = 12.sp, color = IosTextSecondary)
                        }
                        
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(IosBackground)
                                .padding(2.dp)
                        ) {
                            val modeDefault = viewModel.backgroundMode == "default"
                            Text(
                                "默认",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (modeDefault) Color.White else IosTextSecondary,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (modeDefault) IosBlue.copy(alpha = 0.3f) else Color.Transparent)
                                    .clickable { viewModel.updateBackgroundMode("default") }
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                            Text(
                                "自定",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (!modeDefault) Color.White else IosTextSecondary,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (!modeDefault) IosBlue.copy(alpha = 0.3f) else Color.Transparent)
                                    .clickable { viewModel.updateBackgroundMode("custom") }
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                    
                    if (viewModel.backgroundMode == "custom") {
                        HorizontalDivider(color = IosBackground, modifier = Modifier.padding(vertical = 16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("选择背景照片", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = IosTextPrimary)
                                Text(
                                    if (viewModel.customBackgroundExists) "已应用自定义背景，点击右侧更换" else "未选择，默认渲染神秘星轨", 
                                    fontSize = 12.sp, 
                                    color = IosTextSecondary
                                )
                            }
                            
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                if (viewModel.customBackgroundExists) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .border(1.dp, IosBorder, RoundedCornerShape(8.dp))
                                    ) {
                                        AsyncImage(
                                            model = File(context.filesDir, "custom_background.jpg"),
                                            contentDescription = "Preview background",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                                
                                Button(
                                    onClick = { backgroundPickerLauncher.launch("image/*") },
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = IosBlue.copy(alpha = 0.2f),
                                        contentColor = IosBlue
                                    ),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                    modifier = Modifier.height(36.dp)
                                ) {
                                    Text(
                                        text = if (viewModel.customBackgroundExists) "更换" else "选择照片", 
                                        fontSize = 12.sp, 
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        
                        HorizontalDivider(color = IosBackground, modifier = Modifier.padding(vertical = 16.dp))
                        
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("背景透明度", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = IosTextPrimary)
                                Text("${(viewModel.backgroundOpacity * 100).toInt()}%", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = IosBlue)
                            }
                            Spacer(Modifier.height(8.dp))
                            Slider(
                                value = viewModel.backgroundOpacity,
                                onValueChange = { viewModel.updateBackgroundOpacity(it) },
                                valueRange = 0.05f..0.95f,
                                colors = SliderDefaults.colors(
                                    thumbColor = IosBlue,
                                    activeTrackColor = IosBlue,
                                    inactiveTrackColor = IosBackground
                                )
                            )
                        }
                    }
                }
            }
            
            Spacer(Modifier.height(24.dp))
            
            Text(
                "开发者 & 官方网站", 
                fontSize = 14.sp, 
                fontWeight = FontWeight.Bold, 
                color = IosTextSecondary, 
                modifier = Modifier.padding(horizontal = 36.dp).padding(bottom = 12.dp)
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Surface(
                    onClick = {
                        try {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://lsq001.pages.dev")))
                        } catch (e: Exception) {
                            Toast.makeText(context, "未找到浏览器", Toast.LENGTH_SHORT).show()
                        }
                    },
                    shape = RoundedCornerShape(24.dp), 
                    color = IosCard, 
                    border = BorderStroke(1.dp, IosBorder),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        Modifier.padding(vertical = 20.dp, horizontal = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(Modifier.size(48.dp).clip(CircleShape).background(IosBackground), contentAlignment=Alignment.Center) {
                            Text("🌐", fontSize=24.sp)
                        }
                        Spacer(Modifier.height(12.dp))
                        Text("官方网站", fontWeight=FontWeight.Bold, fontSize=16.sp, color=IosTextPrimary)
                        Text("https://lsq001.pages.dev", fontSize=10.sp, color=IosTextSecondary)
                    }
                }
                
                Surface(
                    onClick = {
                        try {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://lsq001.pages.dev")))
                        } catch (e: Exception) {
                            Toast.makeText(context, "未找到浏览器", Toast.LENGTH_SHORT).show()
                        }
                    },
                    shape = RoundedCornerShape(24.dp), 
                    color = IosCard, 
                    border = BorderStroke(1.dp, IosBorder),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        Modifier.padding(vertical = 20.dp, horizontal = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(Modifier.size(48.dp).clip(CircleShape).background(IosBackground), contentAlignment = Alignment.Center) {
                            Text("💖", fontSize=24.sp)
                        }
                        Spacer(Modifier.height(12.dp))
                        Text("支持捐赠", fontWeight=FontWeight.Bold, fontSize=16.sp, color=IosTextPrimary)
                        Text("助力项目更好支持", fontSize=10.sp, color=IosTextSecondary)
                    }
                }
            }
        }
        
        if (showPasswordDialog) {
            SetupPasscodeDialog(
                onDismiss = { showPasswordDialog = false },
                onConfirm = { code ->
                    viewModel.setupEncryption(code)
                    showPasswordDialog = false
                    Toast.makeText(context, "日记保护已开启", Toast.LENGTH_SHORT).show()
                }
            )
        }
        
        if (showExportOptions) {
            ExportOptionsDialog(
                entries = entries,
                onDismiss = { showExportOptions = false },
                onConfirm = { ids ->
                    exportSelectedIds = ids
                    showExportOptions = false
                    exportLauncher.launch("backup.zip")
                }
            )
        }
        
        if (showImportOptions) {
            ImportOptionsDialog(
                onDismiss = { showImportOptions = false },
                onConfirm = { overwrite ->
                    importOverwriteMode = overwrite
                    showImportOptions = false
                    importLauncher.launch("application/zip")
                }
            )
        }
    }
}

@Composable
fun SetupPasscodeDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var codeValue by remember { mutableStateOf("") }
    val context = LocalContext.current
    
    var scaleVal by remember { mutableStateOf(0.85f) }
    var alphaVal by remember { mutableStateOf(0f) }
    val animatedScale by animateFloatAsState(
        targetValue = scaleVal,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium)
    )
    val animatedAlpha by animateFloatAsState(
        targetValue = alphaVal,
        animationSpec = tween(300)
    )
    LaunchedEffect(Unit) {
        scaleVal = 1f
        alphaVal = 1f
    }
    
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color(0xFF101322),
            border = BorderStroke(1.dp, IosBorder),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .graphicsLayer {
                    scaleX = animatedScale
                    scaleY = animatedScale
                    alpha = animatedAlpha
                }
        ) {
            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "开启日记加密", 
                    fontSize = 20.sp, 
                    fontWeight = FontWeight.Bold, 
                    color = IosTextPrimary
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "请输入4位数字作为安全锁密码", 
                    fontSize = 14.sp, 
                    color = IosTextSecondary, 
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(24.dp))
                
                OutlinedTextField(
                    value = codeValue,
                    onValueChange = { input ->
                        if (input.all { it.isDigit() } && input.length <= 4) {
                            codeValue = input
                        }
                    },
                    textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = IosTextPrimary),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = IosBlue,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.08f),
                        focusedContainerColor = Color.White.copy(alpha = 0.05f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
                        focusedTextColor = IosTextPrimary,
                        unfocusedTextColor = IosTextPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(64.dp)
                )
                
                Spacer(Modifier.height(32.dp))
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("取消", fontWeight = FontWeight.Bold, color = IosTextSecondary)
                    }
                    Button(
                        onClick = {
                            if (codeValue.length == 4) {
                                onConfirm(codeValue)
                            } else {
                                Toast.makeText(context, "密码必须是4位数字", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = IosBlue),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1.5f)
                    ) {
                        Text("确认", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsItem(
    title: String,
    subtitle: String,
    disabled: Boolean = false,
    onClick: () -> Unit = {},
    icon: ImageVector? = null,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !disabled) { onClick() }
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon, 
                    contentDescription = null, 
                    tint = if (disabled) IosTextSecondary.copy(alpha = 0.5f) else IosBlue,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(16.dp))
            }
            Column {
                Text(
                    text = title, 
                    fontSize = 17.sp, 
                    fontWeight = FontWeight.SemiBold, 
                    color = if (disabled) IosTextPrimary.copy(alpha = 0.5f) else IosTextPrimary
                )
                Text(
                    text = subtitle, 
                    fontSize = 13.sp, 
                    color = if (disabled) IosTextSecondary.copy(alpha = 0.5f) else IosTextSecondary
                )
            }
        }
        if (trailing != null) {
            trailing()
        } else if (!disabled) {
            Icon(Icons.Filled.ChevronRight, "More", tint = IosTextSecondary, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun DiaryEditorScreen(
    viewModel: DiaryViewModel,
    navController: NavController,
    existingId: String? = null
) {
    val entries by viewModel.entries.collectAsState()
    val existingEntry = remember(existingId, entries) {
        existingId?.let { id -> entries.find { it.id == id } }
    }
    
    var content by remember { mutableStateOf(existingEntry?.content ?: "") }
    val selectedUris = remember { mutableStateListOf<Uri>() }
    
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            if (uri != null) selectedUris.add(uri)
        }
    )

    var activePreviewImage by remember { mutableStateOf<Any?>(null) }

    val df = SimpleDateFormat("MM月dd日", Locale.getDefault())
    val dateStr = df.format(Date(existingEntry?.dateMs ?: viewModel.draftDateMs))

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                Row(
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 12.dp), 
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = IosBlue)
                    }
                    Spacer(Modifier.weight(1f))
                    if (existingEntry != null) {
                        IconButton(onClick = { 
                            if (existingId != null) {
                                viewModel.deleteEntry(existingId)
                                navController.popBackStack()
                            }
                        }) {
                            Icon(Icons.Filled.Delete, "Delete", tint = Color.Red.copy(alpha=0.8f))
                        }
                    }
                    TextButton(onClick = {
                        if (content.isNotBlank() || selectedUris.isNotEmpty() || existingEntry?.imagePaths?.isNotEmpty() == true) {
                            viewModel.addEntry(content, selectedUris, existingId)
                        }
                        navController.popBackStack()
                    }) {
                        Text("保存", fontWeight = FontWeight.Bold, fontSize = 17.sp, color = IosBlue)
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { 
                        photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) 
                    }, 
                    containerColor = IosBlue, 
                    shape = CircleShape
                ) {
                    Icon(Icons.Filled.AddPhotoAlternate, "Photo", tint = Color.White)
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .align(Alignment.TopCenter)
                    .widthIn(max = 600.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 8.dp, bottom = 16.dp), 
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(dateStr, fontWeight = FontWeight.Bold, fontSize = 28.sp, color = IosTextPrimary)
                    Spacer(Modifier.width(16.dp))
                    Surface(
                        color = Color.White.copy(alpha = 0.06f),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.12f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                         Text(
                             text = "${existingEntry?.mood ?: viewModel.draftMood} ${existingEntry?.weather ?: viewModel.draftWeather}", 
                             modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), 
                             fontSize = 14.sp,
                             fontWeight = FontWeight.Bold,
                             color = IosTextPrimary
                         )
                    }
                }

                BasicTextField(
                    value = content,
                    onValueChange = { content = it },
                    textStyle = TextStyle(fontSize = 18.sp, color = IosTextPrimary, lineHeight = 28.sp),
                    cursorBrush = SolidColor(IosBlue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 24.dp),
                    decorationBox = { innerTextField ->
                        if (content.isEmpty()) {
                            Text("此刻的想法...", color = IosTextSecondary, fontSize = 18.sp)
                        }
                        innerTextField()
                    }
                )

                val oldImages = existingEntry?.imagePaths ?: emptyList()
                if (selectedUris.isNotEmpty() || oldImages.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp), 
                        horizontalArrangement = Arrangement.spacedBy(12.dp), 
                        contentPadding = PaddingValues(horizontal = 24.dp)
                    ) {
                        items(oldImages) { path ->
                            SecureImage(
                                path = path, 
                                password = viewModel.currentPassword, 
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                onClick = { activePreviewImage = path }
                            )
                        }
                        items(selectedUris) { uri ->
                            Box(
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White.copy(alpha = 0.08f))
                                    .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.12f)), RoundedCornerShape(16.dp))
                                    .clickable { activePreviewImage = uri },
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = uri, 
                                    contentDescription = null, 
                                    modifier = Modifier.fillMaxSize(), 
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }
        }

        if (activePreviewImage != null) {
            LightboxDialog(
                model = activePreviewImage,
                password = viewModel.currentPassword,
                onDismiss = { activePreviewImage = null }
            )
        }
    }
}

fun isSameDay(ms1: Long, ms2: Long): Boolean {
    val cal1 = Calendar.getInstance().apply { timeInMillis = ms1 }
    val cal2 = Calendar.getInstance().apply { timeInMillis = ms2 }
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

@Composable
fun ExportOptionsDialog(
    entries: List<DiaryEntry>,
    onDismiss: () -> Unit,
    onConfirm: (selectedIds: List<String>?) -> Unit
) {
    var exportAll by remember { mutableStateOf(true) }
    val selectedIds = remember { mutableStateListOf<String>() }
    
    LaunchedEffect(entries) {
        selectedIds.clear()
        selectedIds.addAll(entries.map { it.id })
    }
    
    var scaleVal by remember { mutableStateOf(0.85f) }
    var alphaVal by remember { mutableStateOf(0f) }
    val animatedScale by animateFloatAsState(
        targetValue = scaleVal,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
        label = "scale"
    )
    val animatedAlpha by animateFloatAsState(
        targetValue = alphaVal,
        animationSpec = tween(300),
        label = "alpha"
    )
    LaunchedEffect(Unit) {
        scaleVal = 1f
        alphaVal = 1f
    }
    
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color(0xFF101322),
            border = BorderStroke(1.dp, IosBorder),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .graphicsLayer {
                    scaleX = animatedScale
                    scaleY = animatedScale
                    alpha = animatedAlpha
                }
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "安全备份导出", 
                    fontSize = 20.sp, 
                    fontWeight = FontWeight.Bold, 
                    color = IosTextPrimary
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "可选择导出全部日记或挑选部分生成备份包", 
                    fontSize = 13.sp, 
                    color = IosTextSecondary, 
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))
                
                Surface(
                    onClick = { exportAll = true },
                    shape = RoundedCornerShape(16.dp),
                    color = if (exportAll) IosBlue.copy(alpha = 0.12f) else Color.White.copy(alpha = 0.03f),
                    border = BorderStroke(
                        width = if (exportAll) 2.dp else 1.dp,
                        color = if (exportAll) IosBlue else Color.White.copy(alpha = 0.08f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        RadioButton(
                            selected = exportAll,
                            onClick = { exportAll = true },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = IosBlue,
                                unselectedColor = Color.White.copy(alpha = 0.3f)
                            )
                        )
                        Column {
                            Text(
                                "导出全部日记", 
                                fontSize = 16.sp, 
                                fontWeight = FontWeight.Bold, 
                                color = IosTextPrimary
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "一键打包备份当前全部的文字日记与关联的媒体照片", 
                                fontSize = 12.sp, 
                                color = IosTextSecondary
                            )
                        }
                    }
                }
                
                Spacer(Modifier.height(8.dp))
                
                Surface(
                    onClick = { exportAll = false },
                    shape = RoundedCornerShape(16.dp),
                    color = if (!exportAll) IosBlue.copy(alpha = 0.12f) else Color.White.copy(alpha = 0.03f),
                    border = BorderStroke(
                        width = if (!exportAll) 2.dp else 1.dp,
                        color = if (!exportAll) IosBlue else Color.White.copy(alpha = 0.08f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        RadioButton(
                            selected = !exportAll,
                            onClick = { exportAll = false },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = IosBlue,
                                unselectedColor = Color.White.copy(alpha = 0.3f)
                            )
                        )
                        Column {
                            Text(
                                "导出指定日记", 
                                fontSize = 16.sp, 
                                fontWeight = FontWeight.Bold, 
                                color = IosTextPrimary
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "手动勾选特定的日记回忆并包含其文字与相关图片", 
                                fontSize = 12.sp, 
                                color = IosTextSecondary
                            )
                        }
                    }
                }
                
                AnimatedVisibility(
                    visible = !exportAll,
                    enter = expandVertically(animationSpec = spring()) + fadeIn(),
                    exit = shrinkVertically(animationSpec = spring()) + fadeOut(),
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Spacer(Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)
                        ) {
                            Text(
                                "请进行勾选 (${selectedIds.size}/${entries.size})",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = IosBlue
                            )
                            val isAllChecked = selectedIds.size == entries.size
                            Text(
                                if (isAllChecked) "全不选" else "全选",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = IosBlue,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        if (isAllChecked) {
                                            selectedIds.clear()
                                        } else {
                                            selectedIds.clear()
                                            selectedIds.addAll(entries.map { it.id })
                                        }
                                    }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        
                        Spacer(Modifier.height(8.dp))
                        
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color.Black.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.05f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 200.dp)
                        ) {
                            if (entries.isEmpty()) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("当前暂无任何日记记录", fontSize = 13.sp, color = IosTextSecondary)
                                }
                            } else {
                                val df = remember { SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault()) }
                                LazyColumn(
                                    modifier = Modifier.padding(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    items(entries.size) { index ->
                                        val entry = entries[index]
                                        val isChecked = selectedIds.contains(entry.id)
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(if (isChecked) Color.White.copy(alpha = 0.03f) else Color.Transparent)
                                                .clickable {
                                                    if (isChecked) {
                                                        selectedIds.remove(entry.id)
                                                    } else {
                                                        selectedIds.add(entry.id)
                                                    }
                                                }
                                                .padding(horizontal = 12.dp, vertical = 10.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            Checkbox(
                                                checked = isChecked,
                                                onCheckedChange = { checked ->
                                                    if (checked == true) {
                                                        if (!selectedIds.contains(entry.id)) {
                                                            selectedIds.add(entry.id)
                                                        }
                                                    } else {
                                                        selectedIds.remove(entry.id)
                                                    }
                                                },
                                                colors = CheckboxDefaults.colors(
                                                    checkedColor = IosBlue,
                                                    uncheckedColor = Color.White.copy(alpha = 0.2f),
                                                    checkmarkColor = Color.Black
                                                ),
                                                modifier = Modifier.size(20.dp)
                                            )
                                            
                                            Column(modifier = Modifier.weight(1f)) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                                ) {
                                                    Text(
                                                        text = "${entry.mood} ${entry.weather}",
                                                        fontSize = 14.sp
                                                    )
                                                    Text(
                                                        text = df.format(Date(entry.dateMs)),
                                                        fontSize = 12.sp,
                                                        fontWeight = FontWeight.Medium,
                                                        color = IosTextSecondary
                                                    )
                                                }
                                                Spacer(Modifier.height(2.dp))
                                                Text(
                                                    text = entry.content.replace('\n', ' ').trim(),
                                                    fontSize = 13.sp,
                                                    color = IosTextPrimary.copy(alpha = 0.85f),
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                Spacer(Modifier.height(20.dp))
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = IosTextSecondary
                        ),
                        modifier = Modifier.weight(1f).height(52.dp)
                    ) {
                        Text("取消", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                    val isConfirmEnabled = exportAll || selectedIds.isNotEmpty()
                    IosButton(
                        onClick = {
                            if (exportAll) {
                                onConfirm(null)
                            } else {
                                onConfirm(selectedIds.toList())
                            }
                        },
                        text = "开始导出",
                        enabled = isConfirmEnabled,
                        modifier = Modifier.weight(1.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun ImportOptionsDialog(
    onDismiss: () -> Unit,
    onConfirm: (overwrite: Boolean) -> Unit
) {
    var overwriteMode by remember { mutableStateOf(false) }
    
    var scaleVal by remember { mutableStateOf(0.85f) }
    var alphaVal by remember { mutableStateOf(0f) }
    val animatedScale by animateFloatAsState(
        targetValue = scaleVal,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
        label = "scale"
    )
    val animatedAlpha by animateFloatAsState(
        targetValue = alphaVal,
        animationSpec = tween(300),
        label = "alpha"
    )
    LaunchedEffect(Unit) {
        scaleVal = 1f
        alphaVal = 1f
    }
    
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color(0xFF101322),
            border = BorderStroke(1.dp, IosBorder),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .graphicsLayer {
                    scaleX = animatedScale
                    scaleY = animatedScale
                    alpha = animatedAlpha
                }
        ) {
            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "安全备份导入", 
                    fontSize = 20.sp, 
                    fontWeight = FontWeight.Bold, 
                    color = IosTextPrimary
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "配置数据恢复逻辑，保障隐私安全", 
                    fontSize = 13.sp, 
                    color = IosTextSecondary, 
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(24.dp))
                
                Surface(
                    onClick = { overwriteMode = false },
                    shape = RoundedCornerShape(16.dp),
                    color = if (!overwriteMode) IosBlue.copy(alpha = 0.12f) else Color.White.copy(alpha = 0.03f),
                    border = BorderStroke(
                        width = if (!overwriteMode) 2.dp else 1.dp,
                        color = if (!overwriteMode) IosBlue else Color.White.copy(alpha = 0.08f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        RadioButton(
                            selected = !overwriteMode,
                            onClick = { overwriteMode = false },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = IosBlue,
                                unselectedColor = Color.White.copy(alpha = 0.3f)
                            )
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "非覆盖合并导入", 
                                fontSize = 16.sp, 
                                fontWeight = FontWeight.Bold, 
                                color = IosTextPrimary
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "安全合并。保留设备上现有的日记，并融入新导入的数据（去重防冲突）。", 
                                fontSize = 12.sp, 
                                color = IosTextSecondary,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
                
                Spacer(Modifier.height(16.dp))
                
                Surface(
                    onClick = { overwriteMode = true },
                    shape = RoundedCornerShape(16.dp),
                    color = if (overwriteMode) Color(0x22FF5252) else Color.White.copy(alpha = 0.03f),
                    border = BorderStroke(
                        width = if (overwriteMode) 2.dp else 1.dp,
                        color = if (overwriteMode) Color(0xFFFF5252) else Color.White.copy(alpha = 0.08f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        RadioButton(
                            selected = overwriteMode,
                            onClick = { overwriteMode = true },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFFFF5252),
                                unselectedColor = Color.White.copy(alpha = 0.3f)
                            )
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "覆盖导入 (危险)", 
                                fontSize = 16.sp, 
                                fontWeight = FontWeight.Bold, 
                                color = Color(0xFFFF5252)
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "覆盖替换。删除当前设备上的所有本地日记与图片，完全替换为导入的备份。", 
                                fontSize = 12.sp, 
                                color = IosTextSecondary,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
                
                if (overwriteMode) {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "⚠️ 警告：覆盖导入将会永久清空当前本地数据，请务必提前确认！",
                        fontSize = 12.sp,
                        color = Color(0xFFFFD54F),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 4.dp),
                        lineHeight = 16.sp
                    )
                }
                
                Spacer(Modifier.height(32.dp))
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("取消", fontWeight = FontWeight.Bold, color = IosTextSecondary)
                    }
                    IosButton(
                        onClick = { onConfirm(overwriteMode) },
                        text = "确认导入",
                        color = if (overwriteMode) Color(0xFFFF5252) else IosBlue,
                        modifier = Modifier.weight(1.5f)
                    )
                }
            }
        }
    }
}
