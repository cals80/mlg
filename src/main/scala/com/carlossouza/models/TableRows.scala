package com.carlossouza.models

case class CityRow(id: Int, city: String = "", lat: Double, lon: Double, distance: Double, country: String = "", localizedCountryName: String = "", zip: String = "", ranking: Int, memberCount: Int)
case class GroupRow(id: Int)
case class MemberRow(id: Int)
case class UserRow(id: Int, name: String, email: String = "", password: String = "", createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
