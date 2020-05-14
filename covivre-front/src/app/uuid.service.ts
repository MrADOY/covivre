import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UuidService {

  constructor(public http: HttpClient) { }

  getUUID(): Observable<any> {
    return this.http.get('https://localhost:8080/uuid/');
  }

  getTemporaryToken(uuid: string): Observable<any>  {
    return this.http.post<any>('https://localhost:8080/uuid/temporary-token', {	uuid});
  }

  alertUsers(temporarytokens: any[]): Observable<any> {
    return this.http.post<any>('https://localhost:8080/users/alert-users', {	uuids : temporarytokens.map(t => t.token)});
  }


}
