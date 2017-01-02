import {Routes} from "@angular/router";
import {NoContentComponent} from "./no-content/no-content.component";

export const ROUTES: Routes = [
  {path: '', component: NoContentComponent},

  {path: '**', component: NoContentComponent},
];
