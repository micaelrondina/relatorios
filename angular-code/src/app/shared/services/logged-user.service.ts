import { take } from 'rxjs/operators';


import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LoggedUser } from './../models/logged-user';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json'
  })
};
@Injectable({
  providedIn: 'root'
})
export class LoggedUserService {

  private readonly API = `${environment.API}usuario`;

  constructor(private http: HttpClient) { }

  getLoggedUser(): Observable<LoggedUser>  {
    return this.http.get<LoggedUser>(`${this.API}`, httpOptions).pipe(take(1));
  }
}
