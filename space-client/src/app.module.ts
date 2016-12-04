import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {AppComponent} from "./components/app/app.component";


@NgModule({
    bootstrap: [AppComponent],
    declarations: [AppComponent],
    providers: [],
    imports: [
        BrowserModule
    ]
})
export class AppModule {
}
