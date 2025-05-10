MoneyMinder - Project Overview
===================================

This document provides an overview of the files included in the MoneyMinder project.

Java Source Files
-----------------
- FinanceManager.java: Main application class. Handles user authentication, menu navigation, and core finance management features.
- User.java: Represents a user, including credentials, budgets, goals, transactions, and reminders.
- Goal.java: Represents a savings goal for a user.
- Reminder.java: Represents a reminder set by the user.
- Transaction.java: Represents a financial transaction (income or expense).
- Budget.java: Represents a budget for a specific category.
- BudgetFactory.java: Interface for creating budget objects.
- DefaultBudgetFactory.java: Default implementation of the BudgetFactory interface.
- GoalCompletionListener.java: Interface for handling goal completion events.
- TransactionListener.java: Interface for handling transaction events.
- PasswordStrategy.java: Interface for password validation strategies.
- BasicPasswordStrategy.java: Basic implementation of password validation.
- EmailStrategy.java: Interface for email validation strategies.
- BasicEmailStrategy.java: Basic implementation of email validation.

Compiled Class Files
--------------------
- *.class: Compiled Java bytecode for the corresponding .java files.

Database File
-------------
- users.db: Serialized user data, including credentials, budgets, goals, transactions, and reminders.

