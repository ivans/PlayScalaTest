package controllers

import play._
import play.mvc._
import models._

object Comments extends Controller with CRUDFor[Comment]