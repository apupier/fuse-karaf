/**
 *  Copyright 2005-2018 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.jboss.fuse.patch.commands;

import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.Completion;
import org.apache.karaf.shell.api.action.Option;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.jboss.fuse.patch.PatchService;
import org.jboss.fuse.patch.commands.completers.InstallPatchCompleter;
import org.jboss.fuse.patch.management.Patch;
import org.jboss.fuse.patch.management.PatchException;

@Service
@Command(scope = "patch", name = "rollback", description = "Rollback a patch installation")
public class RollbackCommand extends PatchCommandSupport {

    @Argument(name = "PATCH", description = "name of the patch to rollback", required = true, multiValued = false)
    @Completion(InstallPatchCompleter.class)
    String patchId;

    @Option(name = "--simulation", description = "Simulates rollback of the patch")
    boolean simulation = false;

    @Override
    protected void doExecute(PatchService service) throws Exception {
        Patch patch = service.getPatch(patchId);
        if (patch == null) {
            throw new PatchException("Patch '" + patchId + "' not found");
        }
        if (!patch.isInstalled()) {
            throw new PatchException("Patch '" + patchId + "' is not installed");
        }
        if (patch.getPatchData().getMigratorBundle() != null) {
            throw new PatchException("Patch '" + patchId + "' does not support rollback");
        }
        service.rollback(patch, simulation, false);
    }

}
