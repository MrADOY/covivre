import { Component, OnInit } from '@angular/core';
import { OneSignalService } from './onesignal.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(public oneSignalService: OneSignalService){

  }

  ngOnInit() {
    this.oneSignalService.init();
  }
}