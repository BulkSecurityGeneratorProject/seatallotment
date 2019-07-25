import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IApplicationLog, ApplicationLog } from 'app/shared/model/application-log.model';
import { ApplicationLogService } from './application-log.service';

@Component({
  selector: 'jhi-application-log-update',
  templateUrl: './application-log-update.component.html'
})
export class ApplicationLogUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    application_no: [],
    action: [],
    action_by: [],
    action_date: []
  });

  constructor(protected applicationLogService: ApplicationLogService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ applicationLog }) => {
      this.updateForm(applicationLog);
    });
  }

  updateForm(applicationLog: IApplicationLog) {
    this.editForm.patchValue({
      id: applicationLog.id,
      application_no: applicationLog.application_no,
      action: applicationLog.action,
      action_by: applicationLog.action_by,
      action_date: applicationLog.action_date != null ? applicationLog.action_date.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const applicationLog = this.createFromForm();
    if (applicationLog.id !== undefined) {
      this.subscribeToSaveResponse(this.applicationLogService.update(applicationLog));
    } else {
      this.subscribeToSaveResponse(this.applicationLogService.create(applicationLog));
    }
  }

  private createFromForm(): IApplicationLog {
    return {
      ...new ApplicationLog(),
      id: this.editForm.get(['id']).value,
      application_no: this.editForm.get(['application_no']).value,
      action: this.editForm.get(['action']).value,
      action_by: this.editForm.get(['action_by']).value,
      action_date:
        this.editForm.get(['action_date']).value != null ? moment(this.editForm.get(['action_date']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplicationLog>>) {
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
