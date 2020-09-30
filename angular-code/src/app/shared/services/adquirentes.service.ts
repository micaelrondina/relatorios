import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Parametro } from '../models/parametro';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { take, tap } from 'rxjs/operators';
import { ApiResponse } from '../../dxcrelatorios/extrato-ec/extrato-ec';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class AdquirentesService {

  private readonly API = `${environment.API}`

  constructor(private http: HttpClient) { }

  getAdquirentes(): Observable<any> {
    return this.http.get<ApiResponse<Parametro>>(`${this.API}adquirentes`, httpOptions).pipe(take(1));
  }


}
