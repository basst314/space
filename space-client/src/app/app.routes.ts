import {Routes} from "@angular/router";
import {NoContentComponent} from "./no-content/no-content.component";
import {DebugClientComponent} from "./debug-client/debug-client.component";

export const ROUTES: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'debug'},

  {path: 'debug', component: DebugClientComponent},

  {path: '**', component: NoContentComponent},
];
