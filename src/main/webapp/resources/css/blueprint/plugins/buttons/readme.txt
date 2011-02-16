====
    Copyright (C) 2010 Ian C. Smith <m4r35n357@gmail.com>

    This file is part of JavaEE6Webapp.

        JavaEE6Webapp is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        JavaEE6Webapp is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with JavaEE6Webapp.  If not, see <http://www.gnu.org/licenses/>.
====

Buttons

* Gives you great looking CSS buttons, for both <a> and <button>.
* Demo: particletree.com/features/rediscovering-the-button-element


Credits
----------------------------------------------------------------

* Created by Kevin Hale [particletree.com]
* Adapted for Blueprint by Olav Bjorkoy [bjorkoy.com]


Usage
----------------------------------------------------------------

1) Add this plugin to lib/settings.yml.
   See compress.rb for instructions.

2) Use the following HTML code to place the buttons on your site:

  <button type="submit" class="button positive">
    <img src="css/blueprint/plugins/buttons/icons/tick.png" alt=""/> Save
  </button>

  <a class="button" href="/password/reset/">
    <img src="css/blueprint/plugins/buttons/icons/key.png" alt=""/> Change Password
  </a>

  <a href="#" class="button negative">
    <img src="css/blueprint/plugins/buttons/icons/cross.png" alt=""/> Cancel
  </a>
