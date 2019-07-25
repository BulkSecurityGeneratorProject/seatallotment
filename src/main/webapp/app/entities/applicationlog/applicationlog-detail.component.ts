import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicationlog } from 'app/shared/model/applicationlog.model';

@Component({
  selector: 'jhi-applicationlog-detail',
  templateUrl: './applicationlog-detail.component.html'
})
export class ApplicationlogDetailComponent implements OnInit {
  applicationlog: IApplicationlog;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ applicationlog }) => {
      this.applicationlog = applicationlog;
    });
  }

  previousState() {
    window.history.back();
  }
}
