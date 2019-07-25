import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInstitute } from 'app/shared/model/institute.model';

@Component({
  selector: 'jhi-institute-detail',
  templateUrl: './institute-detail.component.html'
})
export class InstituteDetailComponent implements OnInit {
  institute: IInstitute;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ institute }) => {
      this.institute = institute;
    });
  }

  previousState() {
    window.history.back();
  }
}
