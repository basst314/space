/*
 * Angular 2 decorators and services
 */
import {Component, ViewEncapsulation} from "@angular/core";
import {AppState} from "./app.service";

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

  constructor(public appState: AppState) {

  }

  ngOnInit() {
    console.log('Initial App State', this.appState.state);
  }

}
