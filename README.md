# DrinkApp 🍹

A modern Android application for drink ordering and delivery management, built with Kotlin and following clean architecture principles.

## 📱 Features

### For Customers
- Browse and search drinks catalog
- Add items to cart with size customization
- Place orders with multiple payment options
- Track order status in real-time
- Manage delivery addresses
- View order history

### For Admins
- Manage product catalog (drinks, categories, sizes)
- Process and update order status
- View revenue statistics and reports
- Manage customer accounts
- Track drink orders analytics

### For Shippers
- View available delivery orders
- Accept and manage deliveries
- Update shipping status
- Track delivery history

## 🏗️ Architecture

This project follows **MVP (Model-View-Presenter)** architecture pattern with modern Android development practices:

- **Presentation Layer**: Activities/Fragments with MVP Contracts
- **Domain Layer**: Repository pattern for business logic
- **Data Layer**: Retrofit for API calls, Room for local storage (planned)

### Key Technologies

- **Language**: Kotlin 100%
- **Architecture**: MVP + Repository Pattern
- **Dependency Injection**: Hilt
- **Networking**: Retrofit + OkHttp
- **Async**: Kotlin Coroutines + Flow
- **UI**: Material Design 3
- **Image Loading**: Coil (planned)

## 📋 Requirements

- Android Studio Hedgehog | 2023.1.1 or later
- Minimum SDK: 24 (Android 7.0)
- Target SDK: 34 (Android 14)
- Kotlin: 1.9.0
- Gradle: 8.0

## 🚀 Getting Started

### Prerequisites

1. Install [Android Studio](https://developer.android.com/studio)
2. Install [Git](https://git-scm.com/)
3. Set up Android SDK (API 34)

### Installation

1. Clone the repository
```bash
git clone <repository-url>
cd DrinkApp
```

2. Open the project in Android Studio
```bash
open -a "Android Studio" DrinkApp
```

3. **Configure sensitive information** (IMPORTANT)
   
   Edit `gradle.properties` and replace the placeholder values with your actual credentials:
   ```properties
   # Backend API URL - Replace with your actual backend URL
   API_KEY_URL_BE=http://your-backend-url:port
   
   # PayPal Client ID - Get from https://developer.paypal.com/dashboard/applications
   API_KEY_ID_PAYPAL=your_paypal_client_id_here
   
   # Exchange Rate API Key - Get from https://www.exchangerate-api.com/
   KEY_EXCHANGERATEAPI=your_exchangerate_api_key_here
   ```
   
   ⚠️ **IMPORTANT**: Do NOT commit your real credentials to Git! Keep them local only.

4. Sync Gradle and build the project
```bash
./gradlew build
```

5. Run the app
   - Connect an Android device or start an emulator
   - Click "Run" in Android Studio or use:
   ```bash
   ./gradlew installDebug
   ```

## 📦 Project Structure

```
DrinkApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/drinkapp/
│   │   │   │   ├── data/              # Data layer
│   │   │   │   │   ├── model/         # Data models
│   │   │   │   │   ├── repository/    # Repository implementations
│   │   │   │   │   └── resource/      # API services & DTOs
│   │   │   │   ├── di/                # Dependency injection modules
│   │   │   │   ├── screen/            # Presentation layer
│   │   │   │   │   ├── admin/         # Admin features
│   │   │   │   │   ├── client/        # Customer features
│   │   │   │   │   ├── shipper/       # Shipper features
│   │   │   │   │   └── common/        # Shared features
│   │   │   │   └── utils/             # Utility classes
│   │   │   └── res/                   # Resources (layouts, drawables, etc.)
│   │   └── test/                      # Unit tests
│   └── build.gradle                   # App-level Gradle config
├── build.gradle                       # Project-level Gradle config
└── README.md                          # This file
```

## 🔧 Configuration

### Firebase Setup (Optional)

1. Create a Firebase project at [Firebase Console](https://console.firebase.google.com/)
2. Download `google-services.json`
3. Place it in `app/` directory
4. Enable required services (Authentication, Cloud Messaging, etc.)

### ProGuard/R8

ProGuard rules are configured in `app/proguard-rules.pro` for release builds.

## 🧪 Testing

### Run Unit Tests
```bash
./gradlew test
```

### Run Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

## 🎨 Code Style

This project follows [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html):

- Use `camelCase` for functions and variables
- Use `PascalCase` for classes
- Prefer `val` over `var`
- Use data classes for models
- 4 spaces for indentation

### Linting
```bash
./gradlew lint
```

## 📚 Documentation

For detailed documentation on specific topics, see:

- [Refactor Plan](../DRINKAPP_REFACTOR_PLAN.md) - Complete refactoring roadmap
- [Hilt DI Guide](../HILT_DI_GUIDE.md) - Dependency injection setup
- [Coroutines Migration](../COROUTINES_MIGRATION_PATTERN.md) - Async programming patterns
- [Activity Update Guide](../ACTIVITY_UPDATE_GUIDE.md) - Activity lifecycle management

## 🔄 Recent Updates

### Phase 1: Critical Fixes ✅
- Fixed memory leaks in Presenters
- Improved Retrofit configuration
- Enhanced error handling
- Added loading states

### Phase 2: Architecture Improvements ✅
- Migrated to Kotlin Coroutines
- Implemented Repository Pattern
- Integrated Hilt Dependency Injection
- Modernized UI with Material Design 3
- Optimized RecyclerViews with DiffUtil

### Phase 3: Advanced Features (In Progress)
- Offline caching with Room
- Enhanced authentication & security
- Image loading optimization
- Input validation improvements

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Commit Message Convention

Follow [Conventional Commits](https://www.conventionalcommits.org/):

```
feat: add new feature
fix: bug fix
docs: documentation changes
style: code style changes
refactor: code refactoring
test: add tests
chore: maintenance tasks
```

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Development Team** - Initial work and ongoing maintenance

## 🙏 Acknowledgments

- Material Design 3 guidelines
- Android Architecture Components
- Kotlin Coroutines documentation
- Hilt documentation

## 📞 Support

For support, please open an issue in the repository or contact the development team.

## 🗺️ Roadmap

- [ ] Implement Room database for offline support
- [ ] Add image caching with Coil
- [ ] Implement push notifications
- [ ] Add analytics and crash reporting
- [ ] Improve accessibility features
- [ ] Add unit and UI tests
- [ ] Implement CI/CD pipeline
- [ ] Add multi-language support

## 📊 Project Status

**Current Version**: 1.0.0-beta  
**Status**: Active Development  
**Last Updated**: February 2026

---

Made with ❤️ using Kotlin and Android
