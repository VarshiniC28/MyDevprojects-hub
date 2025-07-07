from django import forms
from . import models

class EmployeeForm(forms.ModelForm):
    class Meta:
        model = models.Employee
        fields = ["name", "email", "password"]
        widgets = {
            "name": forms.TextInput(attrs={"class": "form-control", "placeholder": "Enter your name"}),
            "email": forms.EmailInput(attrs={"class": "form-control", "placeholder": "Enter your email"}),
            "password": forms.TextInput(attrs={"class": "form-control", "placeholder": "Enter password"})
        }
