import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UuidService {

  constructor(public http: HttpClient) { }

  getUUID() {
    return this.http.get('https://localhost:8080/uuid/');
  }

  getTemporaryToken(uuid: string) {
    return this.http.post<any>('https://localhost:8080/uuid/temporary-token', {	uuid});
  }

  alertUsers(temporarytokens: any[]) {
    return this.http.post<any>('https://localhost:8080/users/alert-users', {	uuids : temporarytokens.map(t => t.token)});
  }


}
