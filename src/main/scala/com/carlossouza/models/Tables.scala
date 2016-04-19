package com.carlossouza.models

// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = City.schema ++ Group.schema ++ Member.schema ++ User.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table City
   *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param city Database column city SqlType(VARCHAR), Length(255,true), Default()
   *  @param lat Database column lat SqlType(DOUBLE)
   *  @param lon Database column lon SqlType(DOUBLE)
   *  @param distance Database column distance SqlType(DOUBLE UNSIGNED)
   *  @param country Database column country SqlType(VARCHAR), Length(2,true), Default()
   *  @param localizedCountryName Database column localized_country_name SqlType(VARCHAR), Length(255,true), Default()
   *  @param zip Database column zip SqlType(VARCHAR), Length(100,true), Default()
   *  @param ranking Database column ranking SqlType(INT UNSIGNED)
   *  @param memberCount Database column member_count SqlType(INT UNSIGNED) */
  /** GetResult implicit for fetching CityRow objects using plain SQL queries */
  implicit def GetResultCityRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Double]): GR[CityRow] = GR{
    prs => import prs._
    CityRow.tupled((<<[Int], <<[String], <<[Double], <<[Double], <<[Double], <<[String], <<[String], <<[String], <<[Int], <<[Int]))
  }
  /** Table description of table city. Objects of this class serve as prototypes for rows in queries. */
  class City(_tableTag: Tag) extends Table[CityRow](_tableTag, "city") {
    def * = (id, city, lat, lon, distance, country, localizedCountryName, zip, ranking, memberCount) <> (CityRow.tupled, CityRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(city), Rep.Some(lat), Rep.Some(lon), Rep.Some(distance), Rep.Some(country), Rep.Some(localizedCountryName), Rep.Some(zip), Rep.Some(ranking), Rep.Some(memberCount)).shaped.<>({r=>import r._; _1.map(_=> CityRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column city SqlType(VARCHAR), Length(255,true), Default() */
    val city: Rep[String] = column[String]("city", O.Length(255,varying=true), O.Default(""))
    /** Database column lat SqlType(DOUBLE) */
    val lat: Rep[Double] = column[Double]("lat")
    /** Database column lon SqlType(DOUBLE) */
    val lon: Rep[Double] = column[Double]("lon")
    /** Database column distance SqlType(DOUBLE UNSIGNED) */
    val distance: Rep[Double] = column[Double]("distance")
    /** Database column country SqlType(VARCHAR), Length(2,true), Default() */
    val country: Rep[String] = column[String]("country", O.Length(2,varying=true), O.Default(""))
    /** Database column localized_country_name SqlType(VARCHAR), Length(255,true), Default() */
    val localizedCountryName: Rep[String] = column[String]("localized_country_name", O.Length(255,varying=true), O.Default(""))
    /** Database column zip SqlType(VARCHAR), Length(100,true), Default() */
    val zip: Rep[String] = column[String]("zip", O.Length(100,varying=true), O.Default(""))
    /** Database column ranking SqlType(INT UNSIGNED) */
    val ranking: Rep[Int] = column[Int]("ranking")
    /** Database column member_count SqlType(INT UNSIGNED) */
    val memberCount: Rep[Int] = column[Int]("member_count")

    /** Uniqueness Index over (city) (database name city) */
    val index1 = index("city", city, unique=true)
  }
  /** Collection-like TableQuery object for table City */
  lazy val City = new TableQuery(tag => new City(tag))

  /** Entity class storing rows of table Group
   *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
  /** GetResult implicit for fetching GroupRow objects using plain SQL queries */
  implicit def GetResultGroupRow(implicit e0: GR[Int]): GR[GroupRow] = GR{
    prs => import prs._
    GroupRow(<<[Int])
  }
  /** Table description of table group. Objects of this class serve as prototypes for rows in queries. */
  class Group(_tableTag: Tag) extends Table[GroupRow](_tableTag, "group") {
    def * = id <> (GroupRow, GroupRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = Rep.Some(id).shaped.<>(r => r.map(_=> GroupRow(r.get)), (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
  }
  /** Collection-like TableQuery object for table Group */
  lazy val Group = new TableQuery(tag => new Group(tag))

  /** Entity class storing rows of table Member
   *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
  /** GetResult implicit for fetching MemberRow objects using plain SQL queries */
  implicit def GetResultMemberRow(implicit e0: GR[Int]): GR[MemberRow] = GR{
    prs => import prs._
    MemberRow(<<[Int])
  }
  /** Table description of table member. Objects of this class serve as prototypes for rows in queries. */
  class Member(_tableTag: Tag) extends Table[MemberRow](_tableTag, "member") {
    def * = id <> (MemberRow, MemberRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = Rep.Some(id).shaped.<>(r => r.map(_=> MemberRow(r.get)), (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
  }
  /** Collection-like TableQuery object for table Member */
  lazy val Member = new TableQuery(tag => new Member(tag))

  /** Entity class storing rows of table User
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param email Database column email SqlType(VARCHAR), Length(255,true), Default()
   *  @param password Database column password SqlType(VARCHAR), Length(255,true), Default()
   *  @param createdAt Database column created_at SqlType(TIMESTAMP)
   *  @param updatedAt Database column updated_at SqlType(TIMESTAMP) */
  /** GetResult implicit for fetching UserRow objects using plain SQL queries */
  implicit def GetResultUserRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp]): GR[UserRow] = GR{
    prs => import prs._
    UserRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table user. Objects of this class serve as prototypes for rows in queries. */
  class User(_tableTag: Tag) extends Table[UserRow](_tableTag, "user") {
    def * = (id, name, email, password, createdAt, updatedAt) <> (UserRow.tupled, UserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), Rep.Some(email), Rep.Some(password), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> UserRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column email SqlType(VARCHAR), Length(255,true), Default() */
    val email: Rep[String] = column[String]("email", O.Length(255,varying=true), O.Default(""))
    /** Database column password SqlType(VARCHAR), Length(255,true), Default() */
    val password: Rep[String] = column[String]("password", O.Length(255,varying=true), O.Default(""))
    /** Database column created_at SqlType(TIMESTAMP) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(TIMESTAMP) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Uniqueness Index over (email) (database name email) */
    val index1 = index("email", email, unique=true)
  }
  /** Collection-like TableQuery object for table User */
  lazy val User = new TableQuery(tag => new User(tag))
}
