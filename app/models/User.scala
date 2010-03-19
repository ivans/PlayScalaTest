package models
 
import java.util._
import javax.persistence._
import play.db.jpa._
 
@Entity
class User(
  var email : String,
  var password : String,
  var fullname : String,
  var isAdmin : Boolean
) extends Model {
  override def toString = fullname + ", " + email
}

object User extends QueryOn[User] {
  //extra finder methods here...
}