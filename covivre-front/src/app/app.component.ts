import { Component, OnInit } from '@angular/core';
import { OneSignalService } from './onesignal.service';
import {UuidService} from './uuid.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  private uuid: string;
  private temporaryTokens: string[] = [];
  private alertedUser: string[] = [];

  constructor(public oneSignalService: OneSignalService, public uuidService: UuidService) {

  }

  ngOnInit() {
    this.uuidService.getUUID().subscribe((value => {
      this.uuid = value.uuid;
      this.oneSignalService.init(value.uuid);
    }));
  }

  getTemporaryToken() {
    this.uuidService.getTemporaryToken(this.uuid).subscribe(value => {
      this.temporaryTokens.push(value);
    });
  }
    alertUser() {
      this.uuidService.alertUsers(this.temporaryTokens).subscribe(value => {
        this.alertedUser.push(value);
      });
    }
}
