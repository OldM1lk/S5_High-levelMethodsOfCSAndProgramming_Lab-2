fun authenticate(login: String, password: String): Int {
    val user = users.find { it.login == login } ?: return 3 // неверный логин
    val inputHash = hashPassword(password, user.salt)
    return if (inputHash == user.passwordHash) 0 else 2 // 0 = успех, 2 = неверный пароль
}