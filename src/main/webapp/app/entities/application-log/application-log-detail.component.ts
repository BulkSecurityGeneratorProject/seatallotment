import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicationLog } from 'app/shared/model/application-log.model';

@Component({
  selector: 'jhi-application-log-detail',
  templateUrl: './application-log-detail.component.html'
})
export class ApplicationLogDetailComponent implements OnInit {
  applicationLog: IApplicationLog;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ applicationLog }) => {
      this.applicationLog = applicationLog;
    });
  }

  previousState() {
    window.history.back();
  }
}
