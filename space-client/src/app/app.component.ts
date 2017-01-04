import {Component, ViewEncapsulation} from "@angular/core";
import {AppState} from "./app.service";
import "./rxjs-operators";

/*
 * App Component
 * Top Level Component
 */
@Component({
  selector: 'space',
  encapsulation: ViewEncapsulation.None,
  styleUrls: [
    'app.component.css'
  ],
  templateUrl: './app.component.html'
})
export class AppComponent {

  title = 'Welcome to Space';

  constructor(private appState: AppState) {

  }

  ngOnInit() {
  }

}
