package com.example.channel.Utilities

const val BASE_URL = "https://chat-channel.herokuapp.com/v1/"
const val SOCKET_URL = "https://chat-channel.herokuapp.com/"

const val URL_REGISTER = "${BASE_URL}account/register"
const val URL_LOGIN = "${BASE_URL}account/login"
const val URL_CREATE_USER = "${BASE_URL}user/add"
const val URL_GET_USER = "${BASE_URL}user/byEmail/"
const val URL_GET_CHANNELS = "${BASE_URL}channel"

//Broadcasts constants
const val BROADCAST_USER_DATA_CHANGE = "BROADCAST-USER_DATA_CHANGE"

