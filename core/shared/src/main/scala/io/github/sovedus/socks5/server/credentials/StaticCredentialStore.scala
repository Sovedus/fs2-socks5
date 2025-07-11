/*
 * Copyright 2025 Sovedus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.sovedus.socks5.server.credentials

import cats.effect.Sync

final class StaticCredentialStore[F[_]: Sync](users: Map[String, String])
    extends CredentialStore[F, UserPasswordCredential] {

  private val F: Sync[F] = implicitly

  override def validate(credential: UserPasswordCredential): F[Boolean] =
    F.delay(users.get(credential.user).contains(credential.password))
}

object StaticCredentialStore {
  def apply[F[_]: Sync](users: Map[String, String]): StaticCredentialStore[F] =
    new StaticCredentialStore(users)
}
