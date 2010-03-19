package models
 
import java.util._
import javax.persistence._
import play.db.jpa._
 
@Entity
class User (
	var email : String,
	var password : String,
	var firstName : String,
	var lastName : String,
	var isAdmin : Boolean,
  	@OneToMany(mappedBy="author", cascade=Array(CascadeType.ALL))
	var posts : List[Post] = new ArrayList

) extends Model {
	def this(email : String, password : String, firstName : String, lastName : String) {
		this(email, password, firstName, lastName, false, new ArrayList)
	}
	override def toString = firstName + " " + lastName + ", " + email
}

object User extends QueryOn[User] {
  //extra finder methods here...
}