package controllers

import play._
import play.mvc._
import models._

object Users extends Controller with CRUDFor[User]