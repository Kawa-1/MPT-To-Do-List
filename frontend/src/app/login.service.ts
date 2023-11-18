import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private httpClient: HttpClient) {}

  authenticate(login: String, password: String): Observable<string> {
    return this.httpClient.post<string>(URL + '/auth/login', {
      login: login,
      password: password,
    });
  }

  register(newUser: RegisterWrapper): Observable<any> {
    return this.httpClient.post<string>(URL + '/auth/register', newUser);
  }
}

export interface RegisterWrapper {
  clientType: String;
  clientName: String;
  clientAddress: String;
  clientPhone: String;
  clientMail: String;
  clientPassword: String;
  clientSpec: String;
}

const URL: string = 'http://localhost:8090';