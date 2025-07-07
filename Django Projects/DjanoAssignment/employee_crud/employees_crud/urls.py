from django.urls import path
from . import views

urlpatterns = [
    path("home", views.home, name="home"),
    path("about/", views.about),
    path("edit/<int:id>", views.edit_employee, name="edit"),
    path("delete/<int:id>", views.delete_employee, name="delete"),
]
