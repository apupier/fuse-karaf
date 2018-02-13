/**
 *  Copyright 2005-2017 Red Hat, Inc.
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
import org.jboss.fuse.patch.commands.completers.UninstallPatchCompleter;
import org.jboss.fuse.patch.management.Patch;
import org.jboss.fuse.patch.management.PatchResult;

@Service
@Command(scope = "patch", name = "install", description = "Install a patch")
public class InstallCommand extends PatchCommandSupport {

    @Argument(name = "PATCH", description = "name of the patch to install", required = true, multiValued = false)
    @Completion(UninstallPatchCompleter.class)
    String patchId;

    @Option(name = "--simulation", description = "Simulates installation of the patch")
    boolean simulation = false;

    @Option(name = "--synchronous", description = "Synchronous installation (use with caution)")
    boolean synchronous;

    @Override
    protected void doExecute(PatchService service) throws Exception {
        Patch patch = super.getPatch(patchId);

        PatchResult result = service.install(patch, simulation, synchronous);
    }

}
