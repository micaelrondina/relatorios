import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ApiResponse, ApiRequest, FiltroConsultar } from '../../dxcrelatorios/extrato-ec/extrato-ec';
import { take } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ExtratoEC } from '../models/extratoEC';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};
@Injectable({
  providedIn: 'root'
})
export class ExtratoECService {



  private readonly API = `${environment.API}`;


  constructor(private http: HttpClient) { }


  getListClubSaude(filtro: ApiRequest<FiltroConsultar>): Observable<ApiResponse<ExtratoEC[]>> {
    return this.http.post<ApiResponse<ExtratoEC[]>>(`${this.API}extrato-ec/listar`, JSON.stringify(filtro), httpOptions).pipe(take(1));
  }



}
