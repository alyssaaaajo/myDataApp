# myDataApp

📱 Project Overview
🏷️ App Name:
MyDataApp

📝 Description:
MyDataApp is an Android application that allows users to register, log in, and manage a list of items using SQLite for data storage. It uses a RecyclerView to display items and provides full CRUD (Create, Read, Update, Delete) functionality.

✨ Features

🔐 User Registration
Create an account with username, email, and password

Email and password validation

Securely store credentials in SQLite

🔑 User Login
Log in with registered credentials

Validate username and password

Maintain user session via SharedPreferences

📦 Item Management
Create: Add items with a title and description

Read: View items in a RecyclerView, loaded from SQLite

Update: Edit existing item details

Delete: Remove items from the list

🛠️ Technical Requirements
💻 Development Environment
Android Studio

Java

Android SDK (API level 21 or higher)

🗃️ Database
SQLite (Local Storage)

Tables:

users

items

🧩 UI Components
🔓 Login Screen
EditText: username, password

Button: Login

TextView: Link to registration

📝 Registration Screen
EditText: username, email, password

Button: Register

TextView: Link to login

🏠 Main Screen
RecyclerView: Displays items

FloatingActionButton: Add new item

📋 Item Detail Screen
EditText: title, description

Button: Save (create/update)

Button: Delete item

👤 User Stories
🔹 As a user, I want to register so that I can create an account.

🔹 As a user, I want to log in so that I can access my data.

🔹 As a user, I want to view a list of my items.

🔹 As a user, I want to add new items to the list.

🔹 As a user, I want to edit existing items.

🔹 As a user, I want to delete items I no longer need.
