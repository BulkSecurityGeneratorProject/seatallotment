import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SeatAllotmentSharedModule } from 'app/shared';
import {
  SpecialityComponent,
  SpecialityDetailComponent,
  SpecialityUpdateComponent,
  SpecialityDeletePopupComponent,
  SpecialityDeleteDialogComponent,
  specialityRoute,
  specialityPopupRoute
} from './';

const ENTITY_STATES = [...specialityRoute, ...specialityPopupRoute];

@NgModule({
  imports: [SeatAllotmentSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SpecialityComponent,
    SpecialityDetailComponent,
    SpecialityUpdateComponent,
    SpecialityDeleteDialogComponent,
    SpecialityDeletePopupComponent
  ],
  entryComponents: [SpecialityComponent, SpecialityUpdateComponent, SpecialityDeleteDialogComponent, SpecialityDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeatAllotmentSpecialityModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
