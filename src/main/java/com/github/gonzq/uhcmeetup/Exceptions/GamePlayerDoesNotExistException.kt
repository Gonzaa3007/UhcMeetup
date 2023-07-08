package com.github.gonzq.uhcmeetup.Exceptions

class GamePlayerDoesNotExistException(name: String?) : Exception("Player $name doesn't exist")