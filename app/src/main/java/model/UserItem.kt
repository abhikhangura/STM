package model

data class UserItem(
    val access: Boolean,
    val address: Address,
    val email: String,
    val id: String,
    val name: String,
    val password: String,
    val phoneNumber: String,
    val username: String
)