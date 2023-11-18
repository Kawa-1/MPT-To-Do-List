import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  emailValue: String = '';
  passwordValue: String = '';

  constructor(private router: Router) {}


  redirectToRegister(): void {
    this.router.navigate(['/register']);
  }

  onLogin(): void {
    console.log(this.emailValue, this.passwordValue);
  }
}
