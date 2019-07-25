import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IApplicationlog, Applicationlog } from 'app/shared/model/applicationlog.model';
import { ApplicationlogService } from './applicationlog.service';

@Component({
  selector: 'jhi-applicationlog-update',
  templateUrl: './applicationlog-update.component.html'
})
export class ApplicationlogUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    application_no: [],
    action: [],
    action_by: [],
    action_date: []
  });

  constructor(protected applicationlogService: ApplicationlogService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ applicationlog }) => {
      this.updateForm(applicationlog);
    });
  }

  updateForm(applicationlog: IApplicationlog) {
    this.editForm.patchValue({
      id: applicationlog.id,
      application_no: applicationlog.application_no,
      action: applicationlog.action,
      action_by: applicationlog.action_by,
      action_date: applicationlog.action_date != null ? applicationlog.action_date.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const applicationlog = this.createFromForm();
    if (applicationlog.id !== undefined) {
      this.subscribeToSaveResponse(this.applicationlogService.update(applicationlog));
    } else {
      this.subscribeToSaveResponse(this.applicationlogService.create(applicationlog));
    }
  }

  private createFromForm(): IApplicationlog {
    return {
      ...new Applicationlog(),
      id: this.editForm.get(['id']).value,
      application_no: this.editForm.get(['application_no']).value,
      action: this.editForm.get(['action']).value,
      action_by: this.editForm.get(['action_by']).value,
      action_date:
        this.editForm.get(['action_date']).value != null ? moment(this.editForm.get(['action_date']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplicationlog>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
