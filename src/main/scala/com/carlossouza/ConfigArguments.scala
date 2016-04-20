package com.carlossouza

/**
  * Created by carlossouza on 4/19/16.
  */
case class ConfigArguments(saveToDatabase: Boolean = false,
                           mode: String = "",
                           country: String = "",
                           city: String = "",
                           max: Int = 10,
                           query: String = ""
                          )