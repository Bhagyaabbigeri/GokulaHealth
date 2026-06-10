# GokulaHealth 🏥🐄 [![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blue.svg)](https://kotlinlang.org/) [![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/) [![Min SDK](https://img.shields.io/badge/Min%20SDK-24-orange.svg)](https://developer.android.com/about/dashboards) [![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**GokulaHealth** is a specialized Android application designed for efficient cattle farm management. It streamlines milk production tracking, healthcare scheduling, and livestock analytics, helping farmers maintain a healthy and productive herd through a modern, data-driven approach.

---

## 🌟 Key Features

### 🐄 Herd Management
* **Digital Livestock Profiles**: Comprehensive tracking of individual cattle details (Breed, Age, Health status).
* **Searchable Database**: Quickly find and manage specific animals within large herds.

### 🥛 Milk Production Tracking
* **Daily Log**: Easy entry for Morning and Evening milk yields per animal.
* **Automated Calculations**: Real-time calculation of total daily production.

### 📊 Advanced Analytics
* **Yield Trends**: Interactive **Line Charts** visualizing production over time (Total, Morning, and Evening).
* **Factory Insights**: **Bar Charts** comparing milk supply across different factories for optimized distribution.
* *Powered by [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)*.

### 💉 Healthcare & Vaccination
* **Smart Scheduler**: Track vaccination history and upcoming doses for every animal.
* **Automated Reminders**: Integrated **WorkManager** and **Notifications** ensure you never miss a critical vaccine.

### 🔐 Offline-First Reliability
* **Robust Data**: Full offline capability using **Room Database**, essential for remote farm locations.
* **Background Sync**: Architecture ready for future cloud synchronization.

---

## 🛠️ Tech Stack & Architecture

* **Language**: 100% [Kotlin](https://kotlinlang.org/)
* **UI Framework**: **XML Layouts** with **Material Design 3**
* **Architecture**: **MVVM** (Model-View-ViewModel) + Clean Architecture
* **Dependency Injection**: [Hilt](https://dagger.dev/hilt/) (Dagger Hilt)
* **Local Database**: [Room Persistence Library](https://developer.android.com/training/data-storage/room)
* **Background Tasks**: [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)
* **Navigation**: [Jetpack Navigation Component](https://developer.android.com/guide/navigation) (Safe Args)
* **View Binding**: Safe and efficient UI interactions.
* **Image Loading**: [Glide](https://github.com/bumptech/glide)

---

## 📂 Project Structure

```text
GokulaHealth/
├── app/
│   ├── src/main/java/com/bhagyashreereddy/gokulahealth/
│   │   ├── data/           # Room entities, DAOs, and Repositories
│   │   ├── di/             # Hilt dependency injection modules
│   │   ├── notification/   # WorkManager and Notification helpers
│   │   ├── ui/             # Fragments & ViewModels (Feature-based)
│   │   │   ├── admin/      # Farm management dashboard
│   │   │   ├── cattle/     # Livestock list and details
│   │   │   ├── graph/      # Performance analytics & Charts
│   │   │   ├── milk/       # Milk production logging
│   │   │   └── vaccination/ # Health & schedule management
│   │   └── utils/          # Formatting & helper classes
│   └── src/main/res/       # Layouts (XML), Navigation Graph, and Assets
```

---

## 🚀 Getting Started

### Prerequisites
* Android Studio **Ladybug** (2024.2.1) or newer.
* Android SDK 34 (Compile Sdk).
* Java 17.

### Installation
1. **Clone the repository**:
   ```bash
   git clone https://github.com/Bhagyaabbigeri/GokulaHealth.git
   ```
2. **Open in Android Studio**: Select the root folder of the project.
3. **Gradle Sync**: Wait for the project to download dependencies.
4. **Run**: Deploy to a physical device or emulator (API 24+).

---

## 🤝 Contributing
Contributions are what make the open source community such an amazing place to learn, inspire, and create.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License
Distributed under the **MIT License**. See `LICENSE` for more information.

---
Developed with ❤️ by [Bhagyashree Reddy](https://github.com/Bhagyaabbigeri)
