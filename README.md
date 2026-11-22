# Wanbo T6R Safe Launcher Phase 2 prototype

這是**原始碼專案，不是已編譯 APK**。Android 9 / API 28 起可安裝，未在實機測試。

## 安全限制
- 只宣告 LEANBACK_LAUNCHER，不宣告 HOME，因此不會成為系統預設首頁。
- 不需要網路、ADB、Root 或其他危險權限。
- 不包含停用套件、移除程式、寫入系統設定或更改首頁的程式碼。
- 使用 PackageManager 列出 TV 可啟動應用程式；部分僅有一般手機啟動入口的 App 可能不顯示。

## 建置
1. 在電腦上以 Android Studio 開啟專案資料夾，安裝 Android SDK 35 與 JDK 17。
2. 同步 Gradle；Build > Build APK(s)。
3. APK 預期在 `app/build/outputs/apk/debug/app-debug.apk`。
4. 安裝前先確認來源可信；首次測試只手動從應用程式列表啟動。
5. 測試方向鍵、OK、返回鍵、YouTube 與瀏覽器啟動；不要設定預設首頁。

## 限制
- 本專案尚未經過實際 Gradle 編譯或 Wanbo T6R 測試。
- 目前為列表式 MVP，尚未實作圖示網格、自訂排序、Wi-Fi 狀態或真正的 tvOS 視覺效果。
- `targetSdk=28` 僅為 Android 9 相容性原型；日後正式發佈應更新 target SDK 並重新測試套件可見性。
- Android 手機上的 Termux 通常無法直接執行此 Android Gradle 專案，建議使用電腦或受信任的雲端建置環境。
