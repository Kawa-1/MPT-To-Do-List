import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  clientRegister: boolean = true;

  clientName: String = '';
  clientAddress: String = '';
  clientPhone: String = '';
  clientMail: String = '';
  clientPassword: String = '';
  clientConfirmPassword: String = '';

  mechanicName: String = '';
  mechanicAddress: String = '';
  mechanicSpec: String = '';
  mechanicMail: String = '';
  mechanicPassword: String = '';
  mechanicConfirmPassword: String = '';

  constructor(private router: Router) {}

  changeClientType(event: any): void {
    let buttonId = event.target.id;
    event.target.classList.add(SELECTED_TYPE_OF_CLIENT);
    if (buttonId === REGISTER_AS_MECHANIC_ID) {
      this.clientRegister = false;
      document
        .querySelector('#' + REGISTER_AS_CLIENT)
        ?.classList.remove(SELECTED_TYPE_OF_CLIENT);
      return;
    }
    this.clientRegister = true;
    document
      .querySelector('#' + REGISTER_AS_MECHANIC_ID)
      ?.classList.remove(SELECTED_TYPE_OF_CLIENT);
  }

  redirectToLogin(): void {
    this.router.navigate(['']);
  }

  onRegister(): void {

  }

}

const REGISTER_AS_MECHANIC_ID = 'registerAsMechanic';
const REGISTER_AS_CLIENT = 'registerAsClient';
const SELECTED_TYPE_OF_CLIENT = 'selectedTypeOfClient';
