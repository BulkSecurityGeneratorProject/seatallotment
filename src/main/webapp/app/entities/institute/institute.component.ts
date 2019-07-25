import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInstitute } from 'app/shared/model/institute.model';
import { AccountService } from 'app/core';
import { InstituteService } from './institute.service';

@Component({
  selector: 'jhi-institute',
  templateUrl: './institute.component.html'
})
export class InstituteComponent implements OnInit, OnDestroy {
  institutes: IInstitute[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected instituteService: InstituteService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.instituteService
      .query()
      .pipe(
        filter((res: HttpResponse<IInstitute[]>) => res.ok),
        map((res: HttpResponse<IInstitute[]>) => res.body)
      )
      .subscribe(
        (res: IInstitute[]) => {
          this.institutes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInInstitutes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInstitute) {
    return item.id;
  }

  registerChangeInInstitutes() {
    this.eventSubscriber = this.eventManager.subscribe('instituteListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
