from django.shortcuts import render, redirect
from . import models
from . import forms

# Create your views here.

def home(request):
    if request.method == "POST":
        emp_form = forms.EmployeeForm(request.POST)
        if emp_form.is_valid():
            emp_form.save()
            emp_form = forms.EmployeeForm()
    else:
        emp_form = forms.EmployeeForm()
        
    employees = models.Employee.objects.all()  # select * from employees
    
    context = {"employees": employees, "emp_form": emp_form}
    
    return render(request, "home.html", context)

def about(request):
    return render(request, "about.html")

def edit_employee(request, id):
    employee = models.Employee.objects.get(id=id)  # select * from employees where id = value
    if request.method == "POST":
        emp_form = forms.EmployeeForm(request.POST, instance=employee)
        if emp_form.is_valid():
            emp_form.save()
            return redirect("home")
    else:
        emp_form = forms.EmployeeForm(instance=employee)
    
    context = {"emp_form": emp_form}
    return render(request, "edit.html", context)

def delete_employee(request, id):
    employee = models.Employee.objects.get(id=id)
    if employee:
        employee.delete()
        return redirect("home")
