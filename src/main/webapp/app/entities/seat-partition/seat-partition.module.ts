import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SeatAllotmentSharedModule } from 'app/shared';
import {
  SeatPartitionComponent,
  SeatPartitionDetailComponent,
  SeatPartitionUpdateComponent,
  SeatPartitionDeletePopupComponent,
  SeatPartitionDeleteDialogComponent,
  seatPartitionRoute,
  seatPartitionPopupRoute
} from './';

const ENTITY_STATES = [...seatPartitionRoute, ...seatPartitionPopupRoute];

@NgModule({
  imports: [SeatAllotmentSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SeatPartitionComponent,
    SeatPartitionDetailComponent,
    SeatPartitionUpdateComponent,
    SeatPartitionDeleteDialogComponent,
    SeatPartitionDeletePopupComponent
  ],
  entryComponents: [
    SeatPartitionComponent,
    SeatPartitionUpdateComponent,
    SeatPartitionDeleteDialogComponent,
    SeatPartitionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeatAllotmentSeatPartitionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
