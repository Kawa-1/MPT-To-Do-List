import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { HttpClient, HttpParams } from '@angular/common/http';
import {lastValueFrom} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private cookieService: CookieService, private httpClient: HttpClient) {}

  setToken(token:string):void{
    this.cookieService.set('Token', token);
  }

  async checkIsLogged(): Promise<boolean> {
    return new Promise((resolve, reject) => {
      // if (this.token != this.cookieService.get('Token')) {
      //   resolve(false);
      // }
      let queryParams = new HttpParams();
      queryParams.append('token', this.cookieService.get('Token'));
      this.httpClient.get<boolean>(URL + '/auth/validate', {params: queryParams
      }).subscribe({
        next: (value) => {
          value = true; //Matoły z backendu nie zwracają value...
        if (value === true) {
          resolve(true);
        }
        resolve(false);
        },
        error: (err) => {
          resolve(false);
          // reject(err);
        }
      });
    });
  }

  logout(): void {
    this.cookieService.delete('Token');
  }
}

const URL: string = 'http://localhost:8090';
